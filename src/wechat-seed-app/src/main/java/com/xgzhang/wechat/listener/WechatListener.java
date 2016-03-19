package com.xgzhang.wechat.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.xgzhang.wechat.Config;

public class WechatListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Config.getInstance().setImageSeverUrl(sce.getServletContext().getInitParameter("imageServer"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}
}
