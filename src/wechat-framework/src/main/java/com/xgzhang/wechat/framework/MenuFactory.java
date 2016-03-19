package com.xgzhang.wechat.framework;

import java.util.List;

import com.xgzhang.wechat.framework.domain.menu.Button;

public interface MenuFactory {
	List<Button> generateMenu();
}
