package com.entiy;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String mobile;
	private String email;
	private String position;
	private CommonsMultipartFile[] mfile;

	public String getName() {
		try {
			return URLDecoder.decode(this.name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		try {
			return URLDecoder.decode(this.position, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public CommonsMultipartFile[] getMfile() {
		return mfile;
	}

	public void setMfile(CommonsMultipartFile[] mfile) {
		this.mfile = mfile;
	}
}
