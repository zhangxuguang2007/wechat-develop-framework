package com.xgzhang.wechat.framework.domain.menu;

import java.util.List;

public class Button {
	private String type;
	private String name;
	private String url;
	private String key;
	private List<Button> sub_button;

	public Button() {
	}

	public Button(String key, String type, String name, String url) {
		this.key = key;
		this.type = type;
		this.name = name;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Button> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}
}
