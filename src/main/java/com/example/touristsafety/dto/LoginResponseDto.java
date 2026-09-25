package com.example.touristsafety.dto;

public class LoginResponseDto {

    private String message;
    private Long userId;
    private String name;
    private String email;
    private String role;
    private Long touristId; // Only set for TOURIST role users

    public LoginResponseDto() {}

    public LoginResponseDto(String message, Long userId, String name, String email, String role, Long touristId) {
        this.message = message;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.touristId = touristId;
    }

    // Convenience constructor for error responses
    public LoginResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getTouristId() { return touristId; }
    public void setTouristId(Long touristId) { this.touristId = touristId; }
}
