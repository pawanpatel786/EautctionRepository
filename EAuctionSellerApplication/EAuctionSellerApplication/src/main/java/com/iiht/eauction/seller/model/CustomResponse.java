package com.iiht.eauction.seller.model;

public class CustomResponse {

	public CustomResponse(int statusCode, String message, String status) {
		this.statusCode = statusCode;
		this.message = message;
		this.status = status;
	}
	private int statusCode;
    private String message;
    private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
	
}
