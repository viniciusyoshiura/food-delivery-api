package com.mycompany.fooddelivery.api.controller.exception.handler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
	ENTITY_IN_USE("/entity-in-use", "Entity in use"),
	BUSINESS_ERROR("/business-error", "Business rule violation"),
	INCOMPREENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
	SYSTEM_ERROR("/system-error","System error"),
	INVALID_DATA("/invalid-data", "Invalid data"),
	MAX_FILE_SIZE_EXCEEDED("/max-file-size-exceeded", "File is too large"); 
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://mycompany.com" + path;
		this.title = title;
	}
	
}
