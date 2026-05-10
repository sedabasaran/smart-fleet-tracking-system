package com.fleettracking.fleet_tracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
	
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, message = "Şifre en az 6 karakter olmalı")
    private String password;

    @Email
    @NotBlank
    private String email;
}
