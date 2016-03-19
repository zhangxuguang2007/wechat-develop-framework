package com.xgzhang.wechat.framework;

import com.xgzhang.wechat.framework.domain.response.ResponseMessage;

public interface MessageProcesser {
	ResponseMessage processText(String text);
	ResponseMessage processImage(byte[] imageBuffer);
	ResponseMessage processEvent(String eventKey);
}
