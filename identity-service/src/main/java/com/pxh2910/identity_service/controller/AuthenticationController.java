package com.pxh2910.identity_service.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.pxh2910.identity_service.dto.request.AuthenticationRequest;
import com.pxh2910.identity_service.dto.request.IntrospectRequest;
import com.pxh2910.identity_service.dto.response.APIResponse;
import com.pxh2910.identity_service.dto.response.AuthenticationResponse;
import com.pxh2910.identity_service.dto.response.IntrospectResponse;
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
	
	@PostMapping("/token")
	APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		var result = authenticationService.authenticate(authenticationRequest);
		
		return APIResponse.<AuthenticationResponse>builder()
				.result(result)
				.build();
	}
	
	@PostMapping("/introspect")
	APIResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
		var result = authenticationService.introspect(request);
		
		return APIResponse.<IntrospectResponse>builder()
				.result(result)
				.build();
	}
	
}
