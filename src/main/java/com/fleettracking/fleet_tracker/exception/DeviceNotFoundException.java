package com.fleettracking.fleet_tracker.exception;

public class DeviceNotFoundException extends RuntimeException {

	public DeviceNotFoundException(Long id) {
		super("Device bulunamadı: id=" + id);
	}

}
