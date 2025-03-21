package com.hcltech.capstone.project.exception;

public class CustomServiceException extends RuntimeException {
	public CustomServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
