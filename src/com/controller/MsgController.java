package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.MsgService;

@Controller
public class MsgController extends SuperController {

	@Autowired
	private MsgService msgService;
	
	@RequestMapping(value = "/pages/system/saveTxt.do")
	@ResponseBody
	public boolean saveTxt() {
		return msgService.writeTxt();
	}

	@RequestMapping(value = "/pages/system/getTxt.do")
	@ResponseBody
	public String getTxt(HttpServletResponse response) {
		super.setResponse(response);
		return msgService.readTxt();
	}
}
