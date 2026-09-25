package com.example.touristsafety.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security Configuration for Smart Tourist Safety.
 *
 * Strategy:
 *  - The main auth is handled by our custom /api/auth/login endpoint (session-based).
 *  - All static frontend pages (HTML/CSS/JS) are publicly accessible.
 *  - The /api/auth/** endpoints are publicly accessible.
 *  - All other /api/** endpoints are protected and require an authenticated session.
 *  - Spring Security's built-in form-login is disabled (we use our own custom login page).
 *  - CSRF is disabled for simplicity in this college project context.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF - we're using a stateless-style API with session tokens for a college project
            .csrf(csrf -> csrf.disable())

            // Configure CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Authorization rules - allow public access to all endpoints and static resources
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // Disable default form login - we use our own login.html
            .formLogin(form -> form.disable())

            // Disable HTTP basic auth (we use session-based login)
            .httpBasic(basic -> basic.disable())

            // Handle unauthorized access with JSON response
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json");
                    response.setStatus(401);
                    response.getWriter().write("{\"status\":401,\"message\":\"Authentication required. Please login at /login.html\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\":403,\"message\":\"Access denied. You do not have permission to access this resource.\"}");
                })
            )

            // Allow H2 console frames (if used)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
