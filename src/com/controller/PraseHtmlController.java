package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.Info;
import com.entiy.BaseInfo;
import com.service.PraseHtmlService;

@Controller
public class PraseHtmlController extends SuperController {

	@Autowired
	private PraseHtmlService praseHtmlService;

	@RequestMapping(value = "/pages/system/resumeChange.do")
	@ResponseBody
	public String resumeChange(HttpServletResponse response, BaseInfo bf) {
		super.setResponse(response);
		return praseHtmlService.getPraseHtmlResult(bf);
	}

	@RequestMapping(value = "/pages/system/getInfo.do")
	@ResponseBody
	public String getInfo(HttpServletResponse response, BaseInfo bf) {
		super.setResponse(response);
		Info info = praseHtmlService.getPersonInfo(bf);
		String str = "";
		if (info.getName() != null) {
			str += info.getName() + "&";
		}
		if (info.getTel() != null) {
			str += info.getTel() + "&";
		}
		if (info.getMail() != null) {
			str += info.getMail();
		}
		if (info.getError() != null) {
			str += info.getError();
		}
		return str;
	}

}
