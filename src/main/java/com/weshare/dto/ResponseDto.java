/************************************************************************
 * Copyright © 2018-2019 Globex Underwriting Services
 * Developed by Seosaph Information Technologies Private Limited, India
************************************************************************/

package com.weshare.dto;

/**
 * File: ResponseDto.java
 * 
 * @author B Salini Kumari
 *
 */
public class ResponseDto {
	private int status;
	private String message;
	private Object data;
	private boolean error;
	
	public ResponseDto() {
	}
	
	public ResponseDto(int status, String message, Object data, boolean error) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
