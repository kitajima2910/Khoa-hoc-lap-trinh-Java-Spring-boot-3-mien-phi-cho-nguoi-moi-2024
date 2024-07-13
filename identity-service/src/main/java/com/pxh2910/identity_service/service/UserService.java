package com.pxh2910.identity_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxh2910.identity_service.dto.request.UserCreationRequest;
import com.pxh2910.identity_service.dto.request.UserUpdateRequest;
import com.pxh2910.identity_service.entity.User;
import com.pxh2910.identity_service.exception.AppException;
import com.pxh2910.identity_service.exception.ErrorCode;
import com.pxh2910.identity_service.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User createUser(UserCreationRequest request) {
		User user = new User();
		
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTED_v2);
		}
		
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());
		
		return userRepository.save(user);
	}
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User getUser(String id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	public User updateUser(String userId, UserUpdateRequest request) {
		User user = getUser(userId);
		
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());
		
		return userRepository.save(user);
	}
	
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
	
}
