package com.fleettracking.fleet_tracker.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.fleettracking.fleet_tracker.entity.Device;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fleettracking.fleet_tracker.dto.request.LocationRequestDto;
import com.fleettracking.fleet_tracker.repository.DeviceRepository;
import com.fleettracking.fleet_tracker.service.location.LocationService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceMovementSimulator {
	private final LocationService locationService;
	private final DeviceRepository deviceRepository;
	private final Random random = new Random();

	@Value("${simulation.enabled:true}")
	private boolean enabled;

	// Her device'ın mevcut konumu
	private final Map<Long, double[]> devicePositions = new HashMap<>();

	@Scheduled(fixedRate = 5000)
	public void simulateMovement() {
		if (!enabled)
			return;

		deviceRepository.findAll().stream().filter(d -> d.getStatus() == Device.DeviceStatus.ACTIVE).forEach(device -> {

			// İlk kez görüyorsak İstanbul
			devicePositions.putIfAbsent(device.getId(), new double[] { 41.0082 + (random.nextDouble() - 0.5) * 0.05,
					28.9784 + (random.nextDouble() - 0.5) * 0.05 });

			double[] pos = devicePositions.get(device.getId());
			pos[0] += (random.nextDouble() - 0.5) * 0.001;
			pos[1] += (random.nextDouble() - 0.5) * 0.001;

			LocationRequestDto dto = new LocationRequestDto();
			dto.setDeviceId(device.getId());
			dto.setLatitude(pos[0]);
			dto.setLongitude(pos[1]);
			dto.setSpeed(random.nextDouble() * 80);
			dto.setHeading(random.nextDouble() * 360);

			try {
				locationService.saveLocation(dto);
				log.debug("Simüle: device={}, lat={}, lon={}", device.getDeviceCode(), pos[0], pos[1]);
			} catch (Exception e) {
				log.warn("Simülasyon hatası: {}", e.getMessage());
			}
		});
	}
}
