package com.pxh2910.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INVALID_KEY(1001, "Uncategoried exception", HttpStatus.BAD_REQUEST),

	USER_EXISTED_v1(1002, "User existed...", HttpStatus.BAD_REQUEST), 
	USER_EXISTED_v2(1002, "Tài khoản đã tồn tại...", HttpStatus.BAD_REQUEST),

	USERNAME_INVALID(1003, "Username must be at least 3 character!", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID(1004, "Mật khẩu ít nhất 8 kí tự!", HttpStatus.BAD_REQUEST),

	USER_NOT_EXISTED(1005, "User not existed...", HttpStatus.NOT_FOUND),

	UNAUTHENTICATED(1006, "Unthenticated", HttpStatus.UNAUTHORIZED),
	
	UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),

	UNCATEGORIED_EXCEPTION(9999, "Uncategoried exception", HttpStatus.INTERNAL_SERVER_ERROR),

	;

	private ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}

	private int code;
	private String message;
	private HttpStatusCode statusCode;

}
