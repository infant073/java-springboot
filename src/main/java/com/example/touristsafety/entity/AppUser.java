package com.example.touristsafety.entity;

import jakarta.persistence.*;

/**
 * AppUser entity - stores login credentials and role for each user.
 * Named 'AppUser' to avoid conflict with Spring Security's built-in 'User' class.
 * A TOURIST role user can be optionally linked to a Tourist record via touristId.
 */
@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // BCrypt hashed password

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // ADMIN, FINANCE_OFFICER, TOURIST

    private String status; // ACTIVE, INACTIVE

    // Optional: link to Tourist record for TOURIST role users
    private Long touristId;

    public enum Role {
        ADMIN, FINANCE_OFFICER, TOURIST
    }

    public AppUser() {}

    public AppUser(String name, String email, String password, Role role, String status, Long touristId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.touristId = touristId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getTouristId() { return touristId; }
    public void setTouristId(Long touristId) { this.touristId = touristId; }
}
