package com.bridgelabz.fundoonotes.response;

import org.apache.http.HttpStatus;

public class Responses {

	private String message;
	private int statusCode;
	private Object details;
	private HttpStatus code;
	
	public Responses(HttpStatus code, String message, Object details) {
		
		this.code = code;
		this.message = message;
		this.details = details;
	}

	public Responses(String message, Object details) {
		
		this.message = message;
		this.details = details;
	}

	public Responses(String message, int statusCode) {
	
		this.message = message;
		this.statusCode = statusCode;
	}

	public Responses(String message, int statusCode, Object details) {
		
		this.message = message;
		this.statusCode = statusCode;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getObject() {
		return details;
	}

	public void setObject(Object object) {
		this.details = object;
	}

}