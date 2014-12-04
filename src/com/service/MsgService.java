package com.service;


public interface MsgService {
	/**
	 * 写消息到文件
	 * 
	 * @param request
	 * @return
	 */
	public boolean writeTxt();

	/**
	 * 读消息到文件
	 * 
	 * @return
	 */
	public String readTxt();
}
