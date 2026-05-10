package com.fleettracking.fleet_tracker.mapper;

import org.springframework.stereotype.Component;

import com.fleettracking.fleet_tracker.dto.request.UserRequestDto;
import com.fleettracking.fleet_tracker.dto.response.UserResponseDto;
import com.fleettracking.fleet_tracker.entity.User;

@Component
public class UserMapper {

	public User toEntity(UserRequestDto dto, String encodedPassword) {
		return User.builder().username(dto.getUsername()).password(encodedPassword).email(dto.getEmail())
				.role(User.Role.VIEWER).build();
	}

	public UserResponseDto toResponseDto(User user) {
		UserResponseDto dto = new UserResponseDto();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole().name());
		dto.setCreatedAt(user.getCreatedAt());
		return dto;
	}

}
