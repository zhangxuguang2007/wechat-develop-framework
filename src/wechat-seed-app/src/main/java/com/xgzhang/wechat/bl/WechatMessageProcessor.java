package com.xgzhang.wechat.bl;

import java.util.ArrayList;
import java.util.List;

import com.xgzhang.wechat.Config;
import com.xgzhang.wechat.db.ImageRepository;
import com.xgzhang.wechat.framework.MessageProcesser;
import com.xgzhang.wechat.framework.domain.response.ResponseMessage;
import com.xgzhang.wechat.framework.domain.response.imp.Article;
import com.xgzhang.wechat.framework.domain.response.imp.NewsMessage;
import com.xgzhang.wechat.framework.domain.response.imp.TextMessage;

public class WechatMessageProcessor implements MessageProcesser{
	private ImageRepository _imageRepository;
	
	public WechatMessageProcessor(ImageRepository imageRepository){
		this._imageRepository = imageRepository;
	}
	
	@Override
	public ResponseMessage processText(String text) {
		TextMessage textMessage = new TextMessage();
		textMessage.setContent(String.format("Hi guy, you said '%s', :)", text));
		return textMessage;
	}

	@Override
	public ResponseMessage processImage(byte[] imageBuffer) {
		//Save
		this._imageRepository.saveImage(imageBuffer);
		
		//Read
		NewsMessage newsMessage = new NewsMessage();
		List<Article> articleList = new ArrayList<Article>();
		List<String> imageKeys = this._imageRepository.getAllImageKeys();
		for(int i = 0; i < imageKeys.size(); i++){
			Article article = new Article();
			article.setTitle("Image_" + i);
			article.setPicUrl(Config.getInstance().getImageSeverUrl() + imageKeys.get(i));
			article.setUrl(Config.getInstance().getImageSeverUrl() + imageKeys.get(i));
			articleList.add(article);
		}
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}

	@Override
	public ResponseMessage processEvent(String eventKey) {
		MenuKey menuKey = MenuKey.getMenuKey(eventKey);
		String message = "";
		switch (menuKey) {
		case QUERY:
			message = "Today is a fine day!";
			break;
		case LOGIN:
			message = "Please tell me your name and password!";
			break;
		case LOGOUT:
			message = "Logout successfully!";
			break;
		default:
		}
		TextMessage textMessage = new TextMessage();
		textMessage.setContent(message);
		return textMessage;
	}
}
