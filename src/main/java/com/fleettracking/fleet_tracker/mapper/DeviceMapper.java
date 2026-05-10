package com.fleettracking.fleet_tracker.mapper;

import org.springframework.stereotype.Component;

import com.fleettracking.fleet_tracker.dto.request.DeviceRequestDto;
import com.fleettracking.fleet_tracker.dto.response.DeviceResponseDto;
import com.fleettracking.fleet_tracker.entity.Device;

@Component
public class DeviceMapper {

	public Device toEntity(DeviceRequestDto dto) {
		return Device.builder().deviceCode(dto.getDeviceCode()).name(dto.getName()).type(dto.getType()).status(
				dto.getStatus() != null ? Device.DeviceStatus.valueOf(dto.getStatus()) : Device.DeviceStatus.ACTIVE)
				.build();
	}

	public DeviceResponseDto toResponseDto(Device device) {
		DeviceResponseDto dto = new DeviceResponseDto();
		dto.setId(device.getId());
		dto.setDeviceCode(device.getDeviceCode());
		dto.setName(device.getName());
		dto.setType(device.getType());
		dto.setStatus(device.getStatus() != null ? device.getStatus().name() : null);
		dto.setCreatedAt(device.getCreatedAt());
		return dto;
	}

}
