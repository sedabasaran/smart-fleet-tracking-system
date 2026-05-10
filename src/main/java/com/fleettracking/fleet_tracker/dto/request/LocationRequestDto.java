package com.fleettracking.fleet_tracker.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationRequestDto {

    @NotNull(message = "Device ID gerekli")
    private Long deviceId;

    @NotNull(message = "Latitude gerekli")
    private Double latitude;

    @NotNull(message = "Longitude gerekli")
    private Double longitude;

    private Double speed;
    private Double heading;
    private Double altitude;
	
}
