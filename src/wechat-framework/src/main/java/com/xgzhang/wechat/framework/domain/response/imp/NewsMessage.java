package com.xgzhang.wechat.framework.domain.response.imp;

import java.util.Date;
import java.util.List;

import com.xgzhang.wechat.framework.domain.response.ResponseMessage;

public class NewsMessage extends ResponseMessage {
	private int ArticleCount;
	private List<Article> Articles;
	
	public NewsMessage(){
		setMsgType(RESP_MESSAGE_TYPE_NEWS);
		setCreateTime(new Date().getTime());
	}

	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}