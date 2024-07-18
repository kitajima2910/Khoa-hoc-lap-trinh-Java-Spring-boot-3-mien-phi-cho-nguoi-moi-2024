package com.pxh2910.identity_service.configuration;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxh2910.identity_service.dto.response.APIResponse;
import com.pxh2910.identity_service.exception.ErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
		
		response.setStatus(errorCode.getStatusCode().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		APIResponse<?> apiResponse = APIResponse.builder()
												.code(errorCode.getCode())
												.message(errorCode.getMessage())
												.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
		response.flushBuffer();
		
	}

}
