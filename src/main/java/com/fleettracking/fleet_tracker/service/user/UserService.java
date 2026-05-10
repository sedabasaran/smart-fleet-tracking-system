package com.fleettracking.fleet_tracker.service.user;

import com.fleettracking.fleet_tracker.dto.request.UserRequestDto;
import com.fleettracking.fleet_tracker.dto.response.UserResponseDto;

public interface UserService {

	UserResponseDto register(UserRequestDto dto);

	String login(String username, String password);

}
