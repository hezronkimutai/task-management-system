package com.taskmanagement.controller;

import com.taskmanagement.entity.Role;
import com.taskmanagement.entity.User;
import com.taskmanagement.service.UserService;
import com.taskmanagement.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test controller for user creation and JWT generation.
 */
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Simple test endpoint to verify API connectivity for frontend
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Backend API is running");
        response.put("timestamp", System.currentTimeMillis());
        response.put("environment", System.getProperty("spring.profiles.active", "default"));
        
        return ResponseEntity.ok(response);
    }

    /**
     * Public endpoint to test basic connectivity.
     *
     * @return success message
     */
    @GetMapping("/public")
    public ResponseEntity<Map<String, String>> publicEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Public endpoint working!");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }

    /**
     * Create a test user.
     *
     * @return created user details
     */
    @PostMapping("/create-user")
    public ResponseEntity<Map<String, Object>> createTestUser() {
        try {
            // Check if user already exists
            if (userService.existsByUsername("testuser")) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "User already exists");
                response.put("username", "testuser");
                return ResponseEntity.ok(response);
            }

            // Create test user
            User user = userService.createUser("testuser", "test@example.com", "password123", Role.USER);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User created successfully");
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("createdAt", user.getCreatedAt());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Failed to create user: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Create an admin user.
     *
     * @return created admin user details
     */
    @PostMapping("/create-admin")
    public ResponseEntity<Map<String, Object>> createAdminUser() {
        try {
            // Check if admin already exists
            if (userService.existsByUsername("admin")) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Admin user already exists");
                response.put("username", "admin");
                return ResponseEntity.ok(response);
            }

            // Create admin user
            User admin = userService.createUser("admin", "admin@example.com", "admin123", Role.ADMIN);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Admin user created successfully");
            response.put("id", admin.getId());
            response.put("username", admin.getUsername());
            response.put("email", admin.getEmail());
            response.put("role", admin.getRole());
            response.put("createdAt", admin.getCreatedAt());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Failed to create admin user: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Test JWT generation.
     *
     * @param username the username to generate token for
     * @param password the password for authentication
     * @return JWT token
     */
    @PostMapping("/generate-jwt")
    public ResponseEntity<Map<String, Object>> generateJwt(@RequestParam String username, 
                                                           @RequestParam String password) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "JWT generated successfully");
            response.put("username", username);
            response.put("token", jwt);
            response.put("type", "Bearer");
            
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Authentication failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Failed to generate JWT: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * List all users.
     *
     * @return list of all users
     */
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Users retrieved successfully");
            response.put("count", users.size());
            response.put("users", users);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Failed to retrieve users: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Test endpoint requiring authentication.
     *
     * @return authenticated user message
     */
    @GetMapping("/authenticated")
    public ResponseEntity<Map<String, String>> authenticatedEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "You are authenticated!");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }
}
