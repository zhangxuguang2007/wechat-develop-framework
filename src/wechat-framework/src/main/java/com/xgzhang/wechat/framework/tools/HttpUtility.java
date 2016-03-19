package com.xgzhang.wechat.framework.tools;

import javax.servlet.http.HttpServletRequest;

public class HttpUtility {
	public static String getRootPath(HttpServletRequest servletRequest) {
		return servletRequest.getScheme() + "://" + servletRequest.getServerName() + ":"
				+ servletRequest.getServerPort() + servletRequest.getContextPath();
	}
}
