package com.pxh2910.identity_service.exception;

public enum ErrorCode {

	INVALID_KEY(1001, "Uncategoried exception"),
	
	USER_EXISTED_v1(1002, "User existed..."),
	USER_EXISTED_v2(1002, "Tài khoản đã tồn tại..."),
	USER_EXISTED_CODE(1002),
	USER_EXISTED_MSG("PXH2910"),
	
	USERNAME_INVALID(1003, "Username must be at least 3 character!"),
	PASSWORD_INVALID(1004, "Mật khẩu ít nhất 8 kí tự!"),
	
	UNCATEGORIED_EXCEPTION(9999, "Uncategoried exception"),
	
	;

	private ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private ErrorCode(int code) {
		this.code = code;
	}
	
	private ErrorCode(String message) {
		this.message = message;
	}

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
