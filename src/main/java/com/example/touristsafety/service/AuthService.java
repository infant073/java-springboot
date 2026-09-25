package com.example.touristsafety.service;

import com.example.touristsafety.dto.LoginRequestDto;
import com.example.touristsafety.dto.LoginResponseDto;
import com.example.touristsafety.entity.AppUser;
import com.example.touristsafety.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticate a user by email and password.
     * Returns LoginResponseDto on success, or throws an exception on failure.
     */
    public LoginResponseDto login(LoginRequestDto request) {
        Optional<AppUser> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("INVALID_CREDENTIALS");
        }

        AppUser user = userOpt.get();

        // Check if account is active
        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException("ACCOUNT_INACTIVE");
        }

        // Verify password using BCrypt
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("INVALID_CREDENTIALS");
        }

        return new LoginResponseDto(
                "Login successful",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getTouristId()
        );
    }

    /**
     * Register a new user with BCrypt hashed password.
     */
    public AppUser registerUser(String name, String email, String rawPassword, AppUser.Role role, Long touristId) {
        AppUser user = new AppUser();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword)); // Hash the password
        user.setRole(role);
        user.setStatus("ACTIVE");
        user.setTouristId(touristId);
        return userRepository.save(user);
    }
}
