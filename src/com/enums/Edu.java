package com.enums;

import java.util.ArrayList;
import java.util.List;

public enum Edu {
	MBA, EMBA, 博士, 硕士, 本科, 大专, 中专, 中技, 高中, 初中, 其他;
	public static List<String> toList() {
		List<String> t = new ArrayList<String>();
		t.add(MBA.name());
		t.add(EMBA.name());
		t.add(博士.name());
		t.add(硕士.name());
		t.add(本科.name());
		t.add(大专.name());
		t.add(中专.name());
		t.add(中技.name());
		t.add(高中.name());
		t.add(初中.name());
		t.add(其他.name());
		return t;
	}
}
