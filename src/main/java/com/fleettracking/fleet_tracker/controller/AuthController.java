package com.fleettracking.fleet_tracker.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleettracking.fleet_tracker.common.ApiResponse;
import com.fleettracking.fleet_tracker.dto.request.UserRequestDto;
import com.fleettracking.fleet_tracker.dto.response.UserResponseDto;
import com.fleettracking.fleet_tracker.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(userService.register(dto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody Map<String, String> body) {
        String token = userService.login(body.get("username"), body.get("password"));
        return ResponseEntity.ok(ApiResponse.success(Map.of("token", token)));
    }

}
