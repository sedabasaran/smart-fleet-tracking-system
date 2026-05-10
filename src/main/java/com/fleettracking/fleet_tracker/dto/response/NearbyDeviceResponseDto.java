package com.fleettracking.fleet_tracker.dto.response;

import lombok.Data;

@Data
public class NearbyDeviceResponseDto {

	private Long deviceId;
    private String deviceCode;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double distanceKm;

}
