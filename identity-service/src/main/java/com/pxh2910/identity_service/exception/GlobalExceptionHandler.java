package com.pxh2910.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pxh2910.identity_service.dto.response.APIResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	ResponseEntity<APIResponse<?>> handlingRuntimeException(Exception exception) {
		
		ErrorCode errorCode = ErrorCode.UNCATEGORIED_EXCEPTION;
		
		APIResponse<?> apiResponse = new APIResponse<>();
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());
		
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
	@ExceptionHandler(value = AppException.class)
	ResponseEntity<APIResponse<?>> handlingAppException(AppException exception) {
		
		ErrorCode errorCode = exception.getErrorCode();
		
		APIResponse<?> apiResponse = new APIResponse<>();
//		errorCode.setCode(1002);
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());
		
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<APIResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {
		
		ErrorCode errorCode = ErrorCode.INVALID_KEY;
		
		try {
			errorCode = ErrorCode.valueOf(exception.getFieldError().getDefaultMessage());
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
		
		APIResponse<?> apiResponse = new APIResponse<>();
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());
		
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
}
