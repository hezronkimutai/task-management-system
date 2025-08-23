package com.taskmanagement.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        authResponse = new AuthResponse();
    }

    @Test
    void testAuthResponseConstructor_Default() {
        // When
        AuthResponse response = new AuthResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getToken());
        assertNull(response.getUsername());
        assertNull(response.getEmail());
        assertNull(response.getRole());
        assertEquals("Bearer", response.getType()); // Default type
    }

    @Test
    void testAuthResponseConstructor_WithParameters() {
        // Given
        String token = "mock.jwt.token";
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        String role = "USER";

        // When
        AuthResponse response = new AuthResponse(token, id, username, email, role);

        // Then
        assertEquals(token, response.getToken());
        assertEquals(id, response.getId());
        assertEquals(username, response.getUsername());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        String token = "mock.jwt.token";
        String type = "Bearer";
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        String role = "ADMIN";

        // When
        authResponse.setToken(token);
        authResponse.setType(type);
        authResponse.setId(id);
        authResponse.setUsername(username);
        authResponse.setEmail(email);
        authResponse.setRole(role);

        // Then
        assertEquals(token, authResponse.getToken());
        assertEquals(type, authResponse.getType());
        assertEquals(id, authResponse.getId());
        assertEquals(username, authResponse.getUsername());
        assertEquals(email, authResponse.getEmail());
        assertEquals(role, authResponse.getRole());
    }

    @Test
    void testAuthResponseWithNullValues() {
        // Given
        AuthResponse response = new AuthResponse();

        // When
        response.setToken(null);
        response.setId(null);
        response.setUsername(null);
        response.setEmail(null);
        response.setRole(null);

        // Then
        assertNull(response.getToken());
        assertNull(response.getId());
        assertNull(response.getUsername());
        assertNull(response.getEmail());
        assertNull(response.getRole());
    }

    @Test
    void testAuthResponseWithEmptyStrings() {
        // Given
        AuthResponse response = new AuthResponse();

        // When
        response.setToken("");
        response.setUsername("");
        response.setEmail("");
        response.setRole("");

        // Then
        assertEquals("", response.getToken());
        assertEquals("", response.getUsername());
        assertEquals("", response.getEmail());
        assertEquals("", response.getRole());
    }

    @Test
    void testAuthResponseRoleValues() {
        // Test with different role values
        authResponse.setRole("USER");
        assertEquals("USER", authResponse.getRole());

        authResponse.setRole("ADMIN");
        assertEquals("ADMIN", authResponse.getRole());
    }

    @Test
    void testAuthResponseTypeField() {
        // Test the type field
        assertEquals("Bearer", authResponse.getType()); // Default value

        authResponse.setType("Custom");
        assertEquals("Custom", authResponse.getType());
    }

    @Test
    void testAuthResponseIdField() {
        // Test the id field
        authResponse.setId(123L);
        assertEquals(Long.valueOf(123), authResponse.getId());

        authResponse.setId(0L);
        assertEquals(Long.valueOf(0), authResponse.getId());
    }
}
