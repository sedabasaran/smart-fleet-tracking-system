package com.fleettracking.fleet_tracker.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(Long deviceId) {
        super("Konum bulunamadı: deviceId=" + deviceId);
    }

}
