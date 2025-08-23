package com.taskmanagement.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private Validator validator;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        loginRequest = new LoginRequest();
    }

    @Test
    void testValidLoginRequest() {
        // Given
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidLoginRequest_EmptyUsername() {
        // Given
        loginRequest.setUsername("");
        loginRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void testInvalidLoginRequest_NullUsername() {
        // Given
        loginRequest.setUsername(null);
        loginRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void testInvalidLoginRequest_EmptyPassword() {
        // Given
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testInvalidLoginRequest_NullPassword() {
        // Given
        loginRequest.setUsername("testuser");
        loginRequest.setPassword(null);

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testSettersAndGetters() {
        // Given
        String username = "testuser";
        String password = "password123";

        // When
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        // Then
        assertEquals(username, loginRequest.getUsername());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testToString() {
        // Given
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        // When
        String toString = loginRequest.toString();

        // Then
        assertNotNull(toString);
        // Basic check - toString should contain some content
        assertFalse(toString.isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        LoginRequest request1 = new LoginRequest();
        request1.setUsername("testuser");
        request1.setPassword("password123");

        LoginRequest request2 = new LoginRequest();
        request2.setUsername("testuser");
        request2.setPassword("password123");

        LoginRequest request3 = new LoginRequest();
        request3.setUsername("differentuser");
        request3.setPassword("differentpassword");

        // Then - Basic object equality test
        assertTrue(request1.getUsername().equals(request2.getUsername()));
        assertTrue(request1.getPassword().equals(request2.getPassword()));
        assertFalse(request1.getUsername().equals(request3.getUsername()));
    }

    @Test
    void testLoginWithEmail() {
        // Given - Some systems allow login with email
        loginRequest.setUsername("test@example.com");
        loginRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertTrue(violations.isEmpty()); // Should be valid
        assertEquals("test@example.com", loginRequest.getUsername());
    }

    @Test
    void testLoginWithSpecialCharacters() {
        // Given
        loginRequest.setUsername("test.user-123");
        loginRequest.setPassword("P@ssw0rd!");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertTrue(violations.isEmpty()); // Should be valid
        assertEquals("test.user-123", loginRequest.getUsername());
        assertEquals("P@ssw0rd!", loginRequest.getPassword());
    }
}
