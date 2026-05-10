package com.fleettracking.fleet_tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fleettracking.fleet_tracker.common.ApiResponse;
import com.fleettracking.fleet_tracker.service.geo.GeoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

	private final GeoService geoService;

	@GetMapping("/distance")
	public ResponseEntity<ApiResponse<Double>> calculateDistance(@RequestParam double lat1, @RequestParam double lon1,
			@RequestParam double lat2, @RequestParam double lon2) {
		return ResponseEntity.ok(ApiResponse.success(geoService.calculateDistance(lat1, lon1, lat2, lon2)));
	}

	@GetMapping("/heading")
	public ResponseEntity<ApiResponse<Double>> calculateHeading(@RequestParam double lat1, @RequestParam double lon1,
			@RequestParam double lat2, @RequestParam double lon2) {
		return ResponseEntity.ok(ApiResponse.success(geoService.calculateHeading(lat1, lon1, lat2, lon2)));
	}

}
