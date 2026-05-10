package com.fleettracking.fleet_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fleettracking.fleet_tracker.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	Optional<Location> findTopByDeviceIdOrderByRecordedAtDesc(Long deviceId);

	List<Location> findByDeviceIdOrderByRecordedAtDesc(Long deviceId);

	@Query(value = """
			SELECT l.* FROM locations l
			JOIN (
			    SELECT device_id, MAX(recorded_at) AS max_time
			    FROM locations GROUP BY device_id
			) latest ON l.device_id = latest.device_id
			         AND l.recorded_at = latest.max_time
			WHERE (6371 * acos(
			    LEAST(1.0, cos(radians(:lat)) * cos(radians(l.latitude)) *
			    cos(radians(l.longitude) - radians(:lon)) +
			    sin(radians(:lat)) * sin(radians(l.latitude)))
			)) < :radiusKm
			""", nativeQuery = true)
	
	List<Location> findLocationsWithinRadius(@Param("lat") double lat, @Param("lon") double lon,
			@Param("radiusKm") double radiusKm);

}
