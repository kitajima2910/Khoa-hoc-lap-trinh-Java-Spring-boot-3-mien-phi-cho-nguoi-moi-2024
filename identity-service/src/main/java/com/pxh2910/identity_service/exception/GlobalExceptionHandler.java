package com.pxh2910.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());

		return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	ResponseEntity<APIResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {

		ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

		return ResponseEntity.status(errorCode.getStatusCode())
				.body(APIResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage()).build());

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
