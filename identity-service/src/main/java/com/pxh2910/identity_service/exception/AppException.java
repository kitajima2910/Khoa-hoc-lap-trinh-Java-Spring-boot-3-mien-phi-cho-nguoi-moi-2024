package com.pxh2910.identity_service.exception;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1983731197657869088L;

	public AppException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	private ErrorCode errorCode;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}
