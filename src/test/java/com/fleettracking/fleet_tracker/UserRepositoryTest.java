package com.fleettracking.fleet_tracker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fleettracking.fleet_tracker.entity.User;
import com.fleettracking.fleet_tracker.entity.User.Role;
import com.fleettracking.fleet_tracker.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createUser(String username, String email) {
        return User.builder()
                .username(username)
                .password("password123")
                .email(email)
                .role(Role.ADMIN)
                .build();
    }

    @Test
    void whenValidUser_thenSaved() {

        User saved = userRepository.save(
                createUser("admin", "admin@test.com"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("admin");
    }

    @Test
    void whenFindByUsername_thenReturnsUser() {

        userRepository.save(
                createUser("viewer", "viewer@test.com"));

        Optional<User> found =
                userRepository.findByUsername("viewer");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail())
                .isEqualTo("viewer@test.com");
    }

    @Test
    void whenUsernameExists_thenReturnsTrue() {

        userRepository.save(
                createUser("existingUser", "existing@test.com"));

        boolean exists =
                userRepository.existsByUsername("existingUser");

        assertThat(exists).isTrue();
    }

    @Test
    void whenEmailExists_thenReturnsTrue() {

        userRepository.save(
                createUser("testUser", "mail@test.com"));

        boolean exists =
                userRepository.existsByEmail("mail@test.com");

        assertThat(exists).isTrue();
    }

    @Test
    void whenUsernameNotFound_thenReturnsEmpty() {

        Optional<User> found =
                userRepository.findByUsername("unknown");

        assertThat(found).isEmpty();
    }
}
