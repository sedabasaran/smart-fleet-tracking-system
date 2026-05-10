package com.fleettracking.fleet_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fleettracking.fleet_tracker.common.ApiResponse;
import com.fleettracking.fleet_tracker.dto.request.DeviceRequestDto;
import com.fleettracking.fleet_tracker.dto.response.DeviceResponseDto;
import com.fleettracking.fleet_tracker.service.device.DeviceService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

	private final DeviceService deviceService;

	@PostMapping
	public ResponseEntity<ApiResponse<DeviceResponseDto>> create(@Valid @RequestBody DeviceRequestDto dto) {
		return ResponseEntity.ok(ApiResponse.success(deviceService.createDevice(dto)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DeviceResponseDto>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success(deviceService.getDevice(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<DeviceResponseDto>>> getAll() {
		return ResponseEntity.ok(ApiResponse.success(deviceService.getAllDevices()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<DeviceResponseDto>> update(@PathVariable Long id,
			@Valid @RequestBody DeviceRequestDto dto) {
		return ResponseEntity.ok(ApiResponse.success(deviceService.updateDevice(id, dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		deviceService.deleteDevice(id);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

}
