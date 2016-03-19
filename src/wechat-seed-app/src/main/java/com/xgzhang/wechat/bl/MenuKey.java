package com.xgzhang.wechat.bl;

public enum MenuKey {
	QUERY("QUERY", "Query"),
	LOGIN("LOGIN", "Login"),
	LOGOUT("LOGOUT", "Logout"),
	NONE("NONE", "None");;
	
	private MenuKey(String key, String name){
		this.key = key;
		this.name = name;
	}
	
	private String key;
	private String name;
	
	public String getKey(){
		return this.key;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static MenuKey getMenuKey(String key){
		if(MenuKey.QUERY.getKey().equals(key))
			return MenuKey.QUERY;
		else if(MenuKey.LOGIN.getKey().equals(key))
			return MenuKey.LOGIN;
		else if(MenuKey.LOGOUT.getKey().equals(key))
			return MenuKey.LOGOUT;
		else
			return MenuKey.NONE;
	}
}
