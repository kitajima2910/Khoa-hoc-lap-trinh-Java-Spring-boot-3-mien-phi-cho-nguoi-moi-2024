package com.pxh2910.identity_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pxh2910.identity_service.dto.request.UserCreationRequest;
import com.pxh2910.identity_service.dto.request.UserUpdateRequest;
import com.pxh2910.identity_service.dto.response.UserResponse;
import com.pxh2910.identity_service.entity.User;
import com.pxh2910.identity_service.exception.AppException;
import com.pxh2910.identity_service.exception.ErrorCode;
import com.pxh2910.identity_service.mapper.UserMapper;
import com.pxh2910.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

	UserRepository userRepository;
	UserMapper userMapper;

	public User createUser(UserCreationRequest request) {

		if (userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTED_v2);
		}
		
		User user = new User();
		userMapper.createUser(user, request);

		return userRepository.save(user);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public UserResponse getUser(String id) {
		return userMapper
				.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
	}

	public UserResponse updateUser(String userId, UserUpdateRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		userMapper.updateUser(user, request);

		return userMapper.toUserResponse(userRepository.save(user));
	}

	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}

}
