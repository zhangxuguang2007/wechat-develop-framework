package com.xgzhang.wechat.bl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xgzhang.wechat.framework.domain.response.ResponseMessage;
import com.xgzhang.wechat.framework.domain.response.imp.NewsMessage;
import com.xgzhang.wechat.framework.domain.response.imp.TextMessage;
import com.xgzhang.wechat.framework.tools.IOUtility;

public class WechatMessageProcessorTest {
	private WechatMessageProcessor wechatMessageProcessor;
	
	@Before
	public void init(){
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("service.xml");
		this.wechatMessageProcessor =  (WechatMessageProcessor)applicationContext.getBean("wechatMessageProcessor");
	}
	
	@Test
	public void processText(){
		ResponseMessage reponseMessage = this.wechatMessageProcessor.processText("Anything");
		if(reponseMessage instanceof TextMessage){
			System.out.println(((TextMessage) reponseMessage).getContent());
		}else{
			Assert.fail("Return result is not a TextMessage!");
		}
	}
	
	@Test
	public void processImage(){
		String imagePath = System.getProperty("java.class.path").split(";")[0] + "/image.png";
		byte[] imageBuffer = IOUtility.readImageFromDisc(imagePath);
		for(int i = 0; i < 5; i++)
			this.wechatMessageProcessor.processImage(imageBuffer);
		ResponseMessage reponseMessage = this.wechatMessageProcessor.processImage(imageBuffer);
		if(reponseMessage instanceof NewsMessage){
			NewsMessage newsMessage = (NewsMessage)reponseMessage;
			Assert.assertEquals(6,  newsMessage.getArticleCount());
		}else{
			Assert.fail("Return result is not a NewsMessage!");
		}
	}
	
	@Test
	public void processEvent(){
		ResponseMessage reponseMessage = this.wechatMessageProcessor.processEvent(MenuKey.QUERY.getKey());
		if(reponseMessage instanceof TextMessage){
			TextMessage textMessage = (TextMessage)reponseMessage;
			Assert.assertEquals("Today is a fine day!", textMessage.getContent());
		}else{
			Assert.fail("Return result is not a TextMessage!");
		}
		
		reponseMessage = this.wechatMessageProcessor.processEvent(MenuKey.LOGIN.getKey());
		if(reponseMessage instanceof TextMessage){
			TextMessage textMessage = (TextMessage)reponseMessage;
			Assert.assertEquals("Please tell me your name and password!", textMessage.getContent());
		}else{
			Assert.fail("Return result is not a TextMessage!");
		}
		
		reponseMessage = this.wechatMessageProcessor.processEvent(MenuKey.LOGOUT.getKey());
		if(reponseMessage instanceof TextMessage){
			TextMessage textMessage = (TextMessage)reponseMessage;
			Assert.assertEquals("Logout successfully!", textMessage.getContent());
		}else{
			Assert.fail("Logout successfully!");
		}
	}
}
