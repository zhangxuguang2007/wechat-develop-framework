package com.xgzhang.wechat;

public class Config {
	private static Config _instance;
	
	public static Config getInstance(){
		if(_instance == null)
			_instance = new Config();
		return _instance;
	}
	
	private String imageSeverUrl;

	public String getImageSeverUrl() {
		return imageSeverUrl;
	}

	public void setImageSeverUrl(String _imageSeverUrl) {
		this.imageSeverUrl = _imageSeverUrl;
	}
}
