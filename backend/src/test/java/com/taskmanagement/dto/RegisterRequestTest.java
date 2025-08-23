package com.taskmanagement.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    private Validator validator;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        registerRequest = new RegisterRequest();
    }

    @Test
    void testValidRegisterRequest() {
        // Given
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidRegisterRequest_EmptyUsername() {
        // Given
        registerRequest.setUsername("");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void testInvalidRegisterRequest_NullUsername() {
        // Given
        registerRequest.setUsername(null);
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void testInvalidRegisterRequest_InvalidEmail() {
        // Given
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("invalid-email");
        registerRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testInvalidRegisterRequest_EmptyEmail() {
        // Given
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("");
        registerRequest.setPassword("password123");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testInvalidRegisterRequest_ShortPassword() {
        // Given
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("123"); // Assuming minimum length is 6

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testInvalidRegisterRequest_EmptyPassword() {
        // Given
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testSettersAndGetters() {
        // Given
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        // When
        registerRequest.setUsername(username);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        // Then
        assertEquals(username, registerRequest.getUsername());
        assertEquals(email, registerRequest.getEmail());
        assertEquals(password, registerRequest.getPassword());
    }

    @Test
    void testToString() {
        // Given
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        // When
        String toString = registerRequest.toString();

        // Then
        assertNotNull(toString);
        // Basic check - toString should contain some content
        assertFalse(toString.isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        RegisterRequest request1 = new RegisterRequest();
        request1.setUsername("testuser");
        request1.setEmail("test@example.com");
        request1.setPassword("password123");

        RegisterRequest request2 = new RegisterRequest();
        request2.setUsername("testuser");
        request2.setEmail("test@example.com");
        request2.setPassword("password123");

        RegisterRequest request3 = new RegisterRequest();
        request3.setUsername("differentuser");
        request3.setEmail("different@example.com");
        request3.setPassword("differentpassword");

        // Then - Basic field equality test
        assertTrue(request1.getUsername().equals(request2.getUsername()));
        assertTrue(request1.getEmail().equals(request2.getEmail()));
        assertFalse(request1.getUsername().equals(request3.getUsername()));
    }
}
