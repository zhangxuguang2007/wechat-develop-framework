package com.xgzhang.wechat.framework.domain.menu;

import java.util.List;

public class ButtonWrap {
	private List<Button> button;

	public ButtonWrap(List<Button> button){
		this.button = button;
	}

	public List<Button> getButton() {
		return button;
	}

	public void setButton(List<Button> button) {
		this.button = button;
	}
}
