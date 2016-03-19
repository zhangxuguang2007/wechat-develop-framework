package com.xgzhang.wechat.framework;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xgzhang.wechat.framework.domain.ReceiveEntity;
import com.xgzhang.wechat.framework.domain.response.ResponseMessage;
import com.xgzhang.wechat.framework.tools.DomainUtility;
import com.xgzhang.wechat.framework.tools.IOUtility;
import com.xgzhang.wechat.framework.tools.SecurityUtility;

public class MessageReceiver {
	private final String ACCESS_DENY = "access deny";
	
	private Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	private String _token;
	private MessageProcesser _messageProcessor;
	
	public MessageReceiver(String token, MessageProcesser messageProcessor){
		this._token = token;
		this._messageProcessor = messageProcessor;
	}
	
	public void receiveGetMesseage(HttpServletRequest request, HttpServletResponse response){ 
		response.setContentType("text/plain");
		try{
			String requestSinature = request.getParameter("signature");
	        String echostr = request.getParameter("echostr");  //random string
	        String timestamp = request.getParameter("timestamp");
	        String nonce = request.getParameter("nonce");  //random number
	        
	        if(requestSinature == null || requestSinature.isEmpty()
	        	|| echostr == null || echostr.isEmpty()
	        	|| timestamp == null || timestamp.isEmpty()
	        	|| nonce == null || nonce.isEmpty())
	        {
	        	this._logger.info("Parameter is required!");
	        	response.getWriter().print(ACCESS_DENY);
	        	return;
	        }
	        
	        this._logger.trace(String.format("sinature=%s", requestSinature));
	        this._logger.trace(String.format("echostr=%s", echostr));
	        this._logger.trace(String.format("timestamp=%s", timestamp));
	        this._logger.trace(String.format("nonce=%s", nonce));

	        String[] parameters = { this._token, timestamp, nonce };
	        Arrays.sort(parameters);
	        String bigStr = parameters[0] + parameters[1] + parameters[2];
	        String tokenSinature = SecurityUtility.getDigestOfString(bigStr.getBytes()).toLowerCase();

	        if (tokenSinature.equals(requestSinature)) {
	            response.getWriter().print(echostr);
	        }else{
	        	this._logger.error("Check singature failed");
	        	response.getWriter().print(ACCESS_DENY);
	        }
		}catch(Exception e){
			this._logger.error(ACCESS_DENY, e);
		}
	}
	
	public void receivePostMessage(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/plain");
		try{
			//Encoding
			request.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			response.setCharacterEncoding("UTF-8");
			
			//Request
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			char[] body = new char[request.getContentLength()];
			bufferReader.read(body);
			String requestMessage = new String(body);
			this._logger.info(requestMessage);
			ReceiveEntity receiveEntity = DomainUtility.getReceiveEntity(requestMessage);
			
			this._logger.info(String.format("MessageType = %s", receiveEntity.getMsgType()));
			this._logger.info(String.format("TextContent = %s", receiveEntity.getContent()));
			this._logger.info(String.format("PicRUL = %s", receiveEntity.getPicUrl()));
			this._logger.info(String.format("EventType = %s", receiveEntity.getEventKey()));
			
			//Response
			ResponseMessage reponseMessage = null;
			if(receiveEntity.getMsgType().equals(ReceiveEntity.REQ_MESSAGE_TYPE_TEXT)){
				reponseMessage = this._messageProcessor.processText(receiveEntity.getContent());
			}else if(receiveEntity.getMsgType().equals(ReceiveEntity.REQ_MESSAGE_TYPE_IMAGE)){
				reponseMessage = this._messageProcessor.processImage(IOUtility.downloadImage(receiveEntity.getPicUrl()));
			}else if(receiveEntity.getMsgType().equals(ReceiveEntity.REQ_MESSAGE_TYPE_EVENT)){
				reponseMessage = this._messageProcessor.processEvent(receiveEntity.getEventKey());
			}
			reponseMessage.setFromUserName(receiveEntity.getToUserName());
			reponseMessage.setToUserName(receiveEntity.getFromUserName());
			
			//out
			PrintStream out  = new PrintStream(response.getOutputStream());
			this._logger.info(DomainUtility.messageToXml(reponseMessage));
			out.print(DomainUtility.messageToXml(reponseMessage));
			out.close();
		}catch(Exception e){
			this._logger.error("Process message error", e);
		}
	}
}
