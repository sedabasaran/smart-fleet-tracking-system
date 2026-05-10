package com.fleettracking.fleet_tracker.simulation;

import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fleettracking.fleet_tracker.dto.request.LocationRequestDto;
import com.fleettracking.fleet_tracker.service.location.LocationService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceMovementSimulator {
	private final LocationService locationService;
	private final Random random = new Random();

	@Value("${simulation.enabled:true}")
	private boolean enabled;

	//İstanbul başlangıç koordinatları
	private double lat = 41.0082;
	private double lon = 28.9784;

	@Scheduled(fixedRate = 5000)
	public void simulateMovement() {
		if (!enabled)
			return;

		lat += (random.nextDouble() - 0.5) * 0.001;
		lon += (random.nextDouble() - 0.5) * 0.001;

		LocationRequestDto dto = new LocationRequestDto();
		dto.setDeviceId(1L);
		dto.setLatitude(lat);
		dto.setLongitude(lon);
		dto.setSpeed(random.nextDouble() * 80);
		dto.setHeading(random.nextDouble() * 360);

		try {
			locationService.saveLocation(dto);
			log.debug("Simüle konum: lat={}, lon={}", lat, lon);
		} catch (Exception e) {
			log.warn("Simülasyon atlandı (device hazır değil): {}", e.getMessage());
		}
	}
}
