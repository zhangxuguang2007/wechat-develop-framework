package com.xgzhang.wechat.framework.tools;

import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.xgzhang.wechat.framework.domain.ReceiveEntity;
import com.xgzhang.wechat.framework.domain.response.ResponseMessage;
import com.xgzhang.wechat.framework.domain.response.imp.Article;
import com.xgzhang.wechat.framework.domain.response.imp.MusicMessage;
import com.xgzhang.wechat.framework.domain.response.imp.NewsMessage;
import com.xgzhang.wechat.framework.domain.response.imp.TextMessage;

public class DomainUtility {
	private static Logger logger = LoggerFactory.getLogger(DomainUtility.class);

	public static ReceiveEntity getReceiveEntity(String strXml){
		ReceiveEntity entity = null;
		try {
			if (strXml.length() <= 0 || strXml == null)
				return null;
			strXml = strXml.trim();  //There is blank space after wechat Chinese message
			
			Document document = DocumentHelper.parseText(strXml);
			Element root = document.getRootElement();
			Iterator<?> iter = root.elementIterator();
			
			entity = new ReceiveEntity();
			Class<?> c = Class.forName("com.xgzhang.wechat.framework.domain.ReceiveEntity");
			entity = (ReceiveEntity)c.newInstance();
			
			while(iter.hasNext()){
				Element element = (Element)iter.next();
				Field field = c.getDeclaredField(element.getName());
				Method method = c.getDeclaredMethod("set"+element.getName(), field.getType());
				method.invoke(entity, element.getText());
			}
		} catch (Exception e) {
			System.out.println("xml format error: "+ strXml);
			logger.error(strXml);
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return entity;
	}
	
	//Base message
	public static String messageToXml(ResponseMessage message){
		if(message instanceof TextMessage){
			return textMessageToXml((TextMessage)message);
		}
		else if(message instanceof MusicMessage){
			return musicMessageToXml((MusicMessage)message);
		}if(message instanceof NewsMessage){
			return newsMessageToXml((NewsMessage)message);
		}else{
			return "";
		}
	}
	
	//Text 
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	//Music
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	
	//Text and image
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}
