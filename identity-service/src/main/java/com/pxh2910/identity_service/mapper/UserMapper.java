package com.pxh2910.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pxh2910.identity_service.dto.request.UserCreationRequest;
import com.pxh2910.identity_service.dto.request.UserUpdateRequest;
import com.pxh2910.identity_service.dto.response.UserResponse;
import com.pxh2910.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	User toUser(UserCreationRequest request);
	
	@Mapping(target = "id", ignore = true)
	void createUser(@MappingTarget User user, UserCreationRequest request);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", ignore = true)
	void updateUser(@MappingTarget User user, UserUpdateRequest request);
	
//	@Mapping(source = "firstName", target = "lastName")
//	@Mapping(target = "id", ignore = true)
	UserResponse toUserResponse(User user);
	
}
