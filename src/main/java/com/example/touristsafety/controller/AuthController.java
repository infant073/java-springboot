package com.example.touristsafety.controller;

import com.example.touristsafety.dto.LoginRequestDto;
import com.example.touristsafety.dto.LoginResponseDto;
import com.example.touristsafety.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/auth/login
     * Authenticates a user and returns user info + role.
     * The frontend then redirects to the appropriate dashboard based on role.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request, HttpSession session) {
        try {
            LoginResponseDto response = authService.login(request);

            // Store user info in server-side HTTP session
            session.setAttribute("userId", response.getUserId());
            session.setAttribute("userEmail", response.getEmail());
            session.setAttribute("userRole", response.getRole());
            session.setAttribute("userName", response.getName());

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("timestamp", java.time.LocalDateTime.now().toString());

            if ("ACCOUNT_INACTIVE".equals(ex.getMessage())) {
                errorBody.put("status", 403);
                errorBody.put("message", "Your account is inactive. Please contact the administrator.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody);
            }

            // INVALID_CREDENTIALS or any other error
            errorBody.put("status", 401);
            errorBody.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
        }
    }

    /**
     * POST /api/auth/logout
     * Clears the server-side HTTP session.
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/auth/me
     * Returns current session user info (used by frontend to check login state).
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            Map<String, Object> err = new HashMap<>();
            err.put("status", 401);
            err.put("message", "Not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("userName", session.getAttribute("userName"));
        userInfo.put("userEmail", session.getAttribute("userEmail"));
        userInfo.put("userRole", session.getAttribute("userRole"));
        return ResponseEntity.ok(userInfo);
    }
}
