package com.pxh2910.identity_service.controller;

import java.util.List;

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
	List<User> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/{userId}")
	UserResponse getUser(@PathVariable("userId") String userId) {
		return userService.getUser(userId);
	}
	
	@PutMapping("/{userId}")
	UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
		return userService.updateUser(userId, request);
	}
	
	@DeleteMapping("/{userId}")
	String deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return "User has been deleted";
	}
	
}
