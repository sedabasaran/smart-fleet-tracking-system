package com.fleettracking.fleet_tracker.service.device;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fleettracking.fleet_tracker.dto.request.DeviceRequestDto;
import com.fleettracking.fleet_tracker.dto.response.DeviceResponseDto;
import com.fleettracking.fleet_tracker.entity.Device;
import com.fleettracking.fleet_tracker.exception.DeviceNotFoundException;
import com.fleettracking.fleet_tracker.mapper.DeviceMapper;
import com.fleettracking.fleet_tracker.repository.DeviceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

	private final DeviceRepository deviceRepository;
	private final DeviceMapper deviceMapper;

	@Override
	public DeviceResponseDto createDevice(DeviceRequestDto dto) {
		Device device = deviceMapper.toEntity(dto);
		return deviceMapper.toResponseDto(deviceRepository.save(device));
	}

	@Override
	public DeviceResponseDto getDevice(Long id) {
		return deviceRepository.findById(id).map(deviceMapper::toResponseDto)
				.orElseThrow(() -> new DeviceNotFoundException(id));
	}

	@Override
	public List<DeviceResponseDto> getAllDevices() {
		return deviceRepository.findAll().stream().map(deviceMapper::toResponseDto).collect(Collectors.toList());
	}

	@Override
	public DeviceResponseDto updateDevice(Long id, DeviceRequestDto dto) {
		Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
		device.setName(dto.getName());
		device.setType(dto.getType());
		if (dto.getStatus() != null) {
			device.setStatus(Device.DeviceStatus.valueOf(dto.getStatus()));
		}
		return deviceMapper.toResponseDto(deviceRepository.save(device));
	}

	@Override
	public void deleteDevice(Long id) {
		if (!deviceRepository.existsById(id))
			throw new DeviceNotFoundException(id);
		deviceRepository.deleteById(id);
	}

}
