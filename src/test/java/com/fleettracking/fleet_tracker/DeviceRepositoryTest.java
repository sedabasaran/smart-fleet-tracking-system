package com.fleettracking.fleet_tracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.fleettracking.fleet_tracker.entity.Device;
import com.fleettracking.fleet_tracker.entity.Device.DeviceStatus;
import com.fleettracking.fleet_tracker.repository.DeviceRepository;

@SpringBootTest
@Transactional
public class DeviceRepositoryTest {
	
	@Autowired
    private DeviceRepository deviceRepository;

    private Device validDevice(String deviceCode) {
        return Device.builder()
                .deviceCode(deviceCode)
                .name("Vehicle Tracker")
                .type("GPS")
                .status(DeviceStatus.ACTIVE)
                .build();
    }

    @Test
    void whenSaveDevice_thenPersisted() {

        Device saved =
                deviceRepository.save(validDevice("DEV-001"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDeviceCode()).isEqualTo("DEV-001");
    }

    @Test
    void whenFindByDeviceCode_thenReturnDevice() {

        deviceRepository.save(validDevice("DEV-002"));

        Optional<Device> found =
                deviceRepository.findByDeviceCode("DEV-002");

        assertThat(found).isPresent();
        assertThat(found.get().getDeviceCode())
                .isEqualTo("DEV-002");
    }

    @Test
    void whenDeviceCodeNotExists_thenReturnEmpty() {

        Optional<Device> found =
                deviceRepository.findByDeviceCode("UNKNOWN");

        assertThat(found).isEmpty();
    }

    @Test
    void whenDeviceExists_thenReturnTrue() {

        deviceRepository.save(validDevice("DEV-003"));

        boolean exists =
                deviceRepository.existsByDeviceCode("DEV-003");

        assertThat(exists).isTrue();
    }

    @Test
    void whenDuplicateDeviceCode_thenThrowException() {

        deviceRepository.save(validDevice("DEV-100"));
        deviceRepository.flush();

        assertThrows(DataIntegrityViolationException.class, () -> {

            deviceRepository.save(validDevice("DEV-100"));
            deviceRepository.flush();

        });
    }

}
