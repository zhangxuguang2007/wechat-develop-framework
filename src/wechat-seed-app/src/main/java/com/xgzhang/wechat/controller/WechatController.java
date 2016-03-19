package com.xgzhang.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xgzhang.wechat.framework.MessageProcesser;
import com.xgzhang.wechat.framework.MessageReceiver;

@Controller
@RequestMapping(value="/wechat")
public class WechatController {
	
	private Logger _logger = LoggerFactory.getLogger(this.getClass());
	private MessageReceiver _messageReceiver;
	
	@Autowired
	public WechatController(MessageReceiver messageReceiver, MessageProcesser messageProcessor){
		this._messageReceiver = messageReceiver;
	}
	
	@RequestMapping(value="/receive",method=RequestMethod.GET)
	public void receiveGetMesseage(HttpServletRequest request, HttpServletResponse response){
		this._messageReceiver.receiveGetMesseage(request, response);
	}
	
	@RequestMapping(value="/receive",method=RequestMethod.POST)
	public void receivePostMessage(HttpServletRequest request, HttpServletResponse response){
		this._messageReceiver.receivePostMessage(request, response);
	}
	
}
