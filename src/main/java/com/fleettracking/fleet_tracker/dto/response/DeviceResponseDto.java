package com.fleettracking.fleet_tracker.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DeviceResponseDto {
	
    private Long id;
    private String deviceCode;
    private String name;
    private String type;
    private String status;
    private LocalDateTime createdAt;
    
}
