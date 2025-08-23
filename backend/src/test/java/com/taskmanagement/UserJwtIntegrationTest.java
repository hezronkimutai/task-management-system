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

    // Test output intentionally suppressed to keep test logs clean
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

    // Test output intentionally suppressed to keep test logs clean
    }

    @Test
    void testUserRepository() {
        // Test repository methods
        String username = "repotest" + System.currentTimeMillis();
        String email = "repotest" + System.currentTimeMillis() + "@example.com";

        assertFalse(userService.existsByUsername(username));
        assertFalse(userService.existsByEmail(email));

    userService.createUser(username, email, "password123");

        assertTrue(userService.existsByUsername(username));
        assertTrue(userService.existsByEmail(email));

        assertTrue(userService.findByUsername(username).isPresent());
        assertTrue(userService.findByEmail(email).isPresent());

    // Test output intentionally suppressed to keep test logs clean
    }
}
