package com.controller;

import javax.servlet.http.HttpServletResponse;

public class SuperController {

	/**
	 * 设置不缓存,设置字符编码格式为UTF
	 * @param response
	 */
	protected void setResponse(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html;charset=UTF-8");
	}

}
