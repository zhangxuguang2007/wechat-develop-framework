package com.xgzhang.wechat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xgzhang.wechat.framework.MenuUpdater;

@Controller
@RequestMapping(value="admin")
public class AdminController {
	private MenuUpdater _menuUpdater;
	
	@Autowired
	public AdminController(MenuUpdater menuUpdater) {
		this._menuUpdater = menuUpdater;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String admin(){
		return "admin";
	}
	
	@RequestMapping(value="/generate_menu", method=RequestMethod.POST)
	public String generateMenu(Map<String, Object> map) throws Exception{
		int errorCode = this._menuUpdater.update();
		if(errorCode == 0)
			map.put("generate_menu_result", "Update menu successfully!");
		else
			map.put("generate_menu_result", "Update menu failed!");
		return "admin";
	}
}
