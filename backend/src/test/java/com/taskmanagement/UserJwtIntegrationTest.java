package com.taskmanagement;

import com.taskmanagement.entity.Role;
import com.taskmanagement.entity.User;
import com.taskmanagement.service.UserService;
import com.taskmanagement.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for User entity and JWT security configuration.
 */
@SpringBootTest
@ActiveProfiles("test")
class UserJwtIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    void testCreateUserAndGenerateJWT() {
        // Test user creation
        String username = "testuser" + System.currentTimeMillis();
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password123";

        User user = userService.createUser(username, email, password, Role.USER);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(Role.USER, user.getRole());
        assertNotNull(user.getCreatedAt());
        assertNotEquals(password, user.getPassword()); // Password should be encoded

        // Test authentication and JWT generation
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        assertTrue(authentication.isAuthenticated());

        String jwt = jwtUtils.generateJwtToken(authentication);
        assertNotNull(jwt);
        assertFalse(jwt.isEmpty());

        // Test JWT validation
        assertTrue(jwtUtils.validateJwtToken(jwt));
        assertEquals(username, jwtUtils.getUsernameFromJwtToken(jwt));
        assertFalse(jwtUtils.isTokenExpired(jwt));

        System.out.println("✅ User created successfully:");
        System.out.println("   ID: " + user.getId());
        System.out.println("   Username: " + user.getUsername());
        System.out.println("   Email: " + user.getEmail());
        System.out.println("   Role: " + user.getRole());
        System.out.println("   Created At: " + user.getCreatedAt());
        System.out.println("✅ JWT generated successfully: " + jwt.substring(0, 20) + "...");
    }

    @Test
    void testCreateAdminUserAndGenerateJWT() {
        // Test admin user creation
        String username = "admin" + System.currentTimeMillis();
        String email = "admin" + System.currentTimeMillis() + "@example.com";
        String password = "admin123";

        User admin = userService.createUser(username, email, password, Role.ADMIN);

        assertNotNull(admin);
        assertNotNull(admin.getId());
        assertEquals(username, admin.getUsername());
        assertEquals(email, admin.getEmail());
        assertEquals(Role.ADMIN, admin.getRole());
        assertNotNull(admin.getCreatedAt());

        // Test authentication and JWT generation
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        assertTrue(authentication.isAuthenticated());

        String jwt = jwtUtils.generateJwtToken(authentication);
        assertNotNull(jwt);
        assertTrue(jwtUtils.validateJwtToken(jwt));

        System.out.println("✅ Admin user created successfully:");
        System.out.println("   ID: " + admin.getId());
        System.out.println("   Username: " + admin.getUsername());
        System.out.println("   Email: " + admin.getEmail());
        System.out.println("   Role: " + admin.getRole());
        System.out.println("   Created At: " + admin.getCreatedAt());
        System.out.println("✅ Admin JWT generated successfully: " + jwt.substring(0, 20) + "...");
    }

    @Test
    void testUserRepository() {
        // Test repository methods
        String username = "repotest" + System.currentTimeMillis();
        String email = "repotest" + System.currentTimeMillis() + "@example.com";

        assertFalse(userService.existsByUsername(username));
        assertFalse(userService.existsByEmail(email));

        User user = userService.createUser(username, email, "password123");

        assertTrue(userService.existsByUsername(username));
        assertTrue(userService.existsByEmail(email));

        assertTrue(userService.findByUsername(username).isPresent());
        assertTrue(userService.findByEmail(email).isPresent());

        System.out.println("✅ Repository methods working correctly");
    }
}
