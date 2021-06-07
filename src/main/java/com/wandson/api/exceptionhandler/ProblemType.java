package com.wandson.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
	INCOMPPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"), SYSTEM_ERROR("/system-error", "System error"),
	INVALID_DATA("/invalid-data", "Invalid data");

	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		uri = "https://product-ms.com.br" + path;
		this.title = title;
	}

}
