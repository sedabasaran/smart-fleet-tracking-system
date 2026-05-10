package com.fleettracking.fleet_tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceRequestDto {

    @NotBlank(message = "Device kod boş olamaz")
	private String deviceCode;
	
    @NotBlank(message = "İsim boş olamaz")
    private String name;

    private String type;
    private String status;
    
	
}
