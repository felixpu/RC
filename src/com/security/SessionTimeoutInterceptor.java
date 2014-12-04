package com.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 负责对Ajax提交的/page/systme/*.do的请求进行Session过期判断的拦截器
 * 
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//		if (request.getHeader("x-requested-with") != null
//				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {// 如果是ajax请求响应头会有，x-requested-with；
//			response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
//			response.setStatus(403);
//			return false;
//		}
		return true;
	}

}
