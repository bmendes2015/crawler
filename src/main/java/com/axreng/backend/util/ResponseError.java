package com.axreng.backend.util;

import spark.Response;

public class ResponseError {
	
	private int status;
	private String message;
	
	public ResponseError(int status, String message){
		this.status = status;
		this.message = message;
	}
	
	public ResponseError(Response response, String message){
		this.status = response.status();
		this.message = message;
	}
	
	public ResponseError(Response response, Exception e){
		this.status = response.status();
		this.message = e.getMessage();
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
	
	
}
