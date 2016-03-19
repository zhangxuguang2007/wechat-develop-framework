package com.xgzhang.wechat.framework.domain.response.imp;

import java.util.Date;

import com.xgzhang.wechat.framework.domain.response.ResponseMessage;

public class TextMessage extends ResponseMessage {
	private String Content;
	
	public TextMessage(){
		setMsgType(RESP_MESSAGE_TYPE_TEXT);
		setCreateTime(new Date().getTime());
	}

	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
}