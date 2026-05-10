package com.fleettracking.fleet_tracker.service.device;

import java.util.List;

import com.fleettracking.fleet_tracker.dto.request.DeviceRequestDto;
import com.fleettracking.fleet_tracker.dto.response.DeviceResponseDto;

public interface DeviceService {
	
    DeviceResponseDto createDevice(DeviceRequestDto dto);
    DeviceResponseDto getDevice(Long id);
    List<DeviceResponseDto> getAllDevices();
    DeviceResponseDto updateDevice(Long id, DeviceRequestDto dto);
    void deleteDevice(Long id);

}
