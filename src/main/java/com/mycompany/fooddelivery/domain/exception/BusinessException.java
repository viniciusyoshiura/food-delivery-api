package com.mycompany.fooddelivery.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
