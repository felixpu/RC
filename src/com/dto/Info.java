package com.dto;

import java.io.Serializable;

public class Info implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String tel;
	private String mail;
	private String error;
	
	public Info(String error) {
		super();
		this.error = error;
	}
	public Info(String name, String tel, String mail) {
		super();
		this.name = name;
		this.tel = tel;
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
