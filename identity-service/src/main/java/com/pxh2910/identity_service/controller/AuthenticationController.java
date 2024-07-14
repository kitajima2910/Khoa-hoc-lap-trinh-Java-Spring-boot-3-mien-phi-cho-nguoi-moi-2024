package com.pxh2910.identity_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pxh2910.identity_service.dto.request.AuthenticationRequest;
import com.pxh2910.identity_service.dto.response.APIResponse;
import com.pxh2910.identity_service.dto.response.AuthenticationResponse;
import com.pxh2910.identity_service.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

	AuthenticationService authenticationService;
	
	@PostMapping("/log-in")
	APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		boolean result = authenticationService.authenticate(authenticationRequest);
		
		APIResponse<AuthenticationResponse> apiResponse = new APIResponse<>();
		AuthenticationResponse authenticationResponse = new AuthenticationResponse(result);
		apiResponse.setResult(authenticationResponse);
		return apiResponse;
		
//		return APIResponse.<AuthenticationResponse>builder()
//				.result(AuthenticationResponse.builder()
//						.authenticated(result)
//						.build())
//				.build();
	}
	
}
