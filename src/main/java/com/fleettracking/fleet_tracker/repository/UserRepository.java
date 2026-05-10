package com.fleettracking.fleet_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleettracking.fleet_tracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
