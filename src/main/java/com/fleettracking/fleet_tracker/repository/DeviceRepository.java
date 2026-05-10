package com.fleettracking.fleet_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleettracking.fleet_tracker.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

	Optional<Device> findByDeviceCode(String deviceCode);

	boolean existsByDeviceCode(String deviceCode);

}
