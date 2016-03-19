package com.xgzhang.wechat.framework.domain.response.imp;

import java.util.Date;

import com.xgzhang.wechat.framework.domain.response.ResponseMessage;

public class MusicMessage extends ResponseMessage {
	private Music Music;
	
	public MusicMessage(){
		setMsgType(RESP_MESSAGE_TYPE_MUSIC);
		setCreateTime(new Date().getTime());
	}

	public Music getMusic() {
		return Music;
	}
	public void setMusic(Music music) {
		Music = music;
	}
}