package com.fleettracking.fleet_tracker.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LocationResponseDto {

    private Long id;
    private Long deviceId;
    private String deviceCode;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private Double heading;
    private LocalDateTime recordedAt;

}
