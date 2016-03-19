package com.xgzhang.wechat.bl;

import java.util.ArrayList;
import java.util.List;

import com.xgzhang.wechat.framework.MenuFactory;
import com.xgzhang.wechat.framework.domain.menu.Button;

public class WechatMenuFactory implements MenuFactory {
	private static final String VIEW = "view";
	private static final String CLICK = "click";
	
	@Override
	public List<Button> generateMenu() {
		List<Button> rootButtonList = new ArrayList<Button>();
		
		Button viewButton = new Button(null, null, "View", null);
		viewButton.setSub_button(new ArrayList<Button>());
		viewButton.getSub_button().add(new Button("", VIEW, "Bing", "http://www.bing.com"));
		viewButton.getSub_button().add(new Button("", VIEW, "Github", "https://github.com/"));
		rootButtonList.add(viewButton);
		
		Button actionButton = new Button(null, null, "Action", null);
		actionButton.setSub_button(new ArrayList<Button>());
		actionButton.getSub_button().add(new Button(MenuKey.QUERY.getKey(), CLICK, MenuKey.QUERY.getName(), null));
		actionButton.getSub_button().add(new Button(MenuKey.LOGIN.getKey(), CLICK, MenuKey.LOGIN.getName(), null));
		actionButton.getSub_button().add(new Button(MenuKey.LOGOUT.getKey(), CLICK, MenuKey.LOGOUT.getName(), null));
		rootButtonList.add(actionButton);
		
		return rootButtonList;
	}
}
