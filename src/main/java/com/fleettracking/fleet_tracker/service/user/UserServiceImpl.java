package com.fleettracking.fleet_tracker.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fleettracking.fleet_tracker.dto.request.UserRequestDto;
import com.fleettracking.fleet_tracker.dto.response.UserResponseDto;
import com.fleettracking.fleet_tracker.entity.User;
import com.fleettracking.fleet_tracker.mapper.UserMapper;
import com.fleettracking.fleet_tracker.repository.UserRepository;
import com.fleettracking.fleet_tracker.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Override
	public UserResponseDto register(UserRequestDto dto) {
		if (userRepository.existsByUsername(dto.getUsername())) {
			throw new RuntimeException("Bu kullanıcı adı zaten alınmış");
		}
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new RuntimeException("Bu email zaten kayıtlı");
		}
		String encoded = passwordEncoder.encode(dto.getPassword());
		User user = userMapper.toEntity(dto, encoded);
		return userMapper.toResponseDto(userRepository.save(user));
	}

	@Override
	public String login(String username, String password) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Şifre hatalı");
		}
		return jwtUtil.generateToken(username);
	}

}
