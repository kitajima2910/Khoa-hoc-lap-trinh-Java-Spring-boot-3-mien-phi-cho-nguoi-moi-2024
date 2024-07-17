package com.pxh2910.identity_service.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pxh2910.identity_service.dto.request.UserCreationRequest;
import com.pxh2910.identity_service.dto.request.UserUpdateRequest;
import com.pxh2910.identity_service.dto.response.APIResponse;
import com.pxh2910.identity_service.dto.response.UserResponse;
import com.pxh2910.identity_service.entity.User;
import com.pxh2910.identity_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

	UserService userService;

	@PostMapping
	APIResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {

		APIResponse<User> apiResponse = new APIResponse<>();
		apiResponse.setResult(userService.createUser(request));

		return apiResponse;
	}

	@GetMapping
	APIResponse<List<User>> getUsers() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		log.info("Username: {}", authentication.getName());
		authentication.getAuthorities().forEach(g -> log.info(g.getAuthority()));
		
		return APIResponse.<List<User>>builder().result(userService.getUsers()).build();
	}

	@GetMapping("/{userId}")
	APIResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
		return APIResponse.<UserResponse>builder().result(userService.getUser(userId)).build();
	}

	@PutMapping("/{userId}")
	APIResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
		return APIResponse.<UserResponse>builder().result(userService.updateUser(userId, request)).build();
	}

	@DeleteMapping("/{userId}")
	APIResponse<String> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return APIResponse.<String>builder().result("User has been deleted").build();
	}

}
