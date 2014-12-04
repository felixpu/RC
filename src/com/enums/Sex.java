package com.enums;

import java.util.ArrayList;
import java.util.List;

public enum Sex {
	男, 女;
	public static List<String> toList() {
		List<String> t = new ArrayList<String>();
		t.add(男.name());
		t.add(女.name());
		return t;
	}
}
