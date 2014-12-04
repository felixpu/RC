package com.enums;

import java.util.ArrayList;
import java.util.List;

public enum Marriage {
	未婚, 已婚, 离异, 保密;
	public static List<String> toList() {
		List<String> t = new ArrayList<String>();
		t.add(未婚.name());
		t.add(已婚.name());
		t.add(离异.name());
		t.add(保密.name());
		return t;
	}
}
