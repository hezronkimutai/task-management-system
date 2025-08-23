package com.taskmanagement.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private final String testSecret = "mySecretKey12345678901234567890123456789012345678901234567890";
    private final int testExpirationMs = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        // Set private fields using reflection
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", testSecret);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", testExpirationMs);
    }

    @Test
    void generateJwtToken_WithAuthentication_ShouldReturnValidToken() {
        // Given
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "testuser", "password", Collections.emptyList());

        // When
        String token = jwtUtils.generateJwtToken(authentication);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.startsWith("eyJ")); // JWT tokens start with this
    }

    @Test
    void generateJwtToken_WithUsername_ShouldReturnValidToken() {
        // Given
        String username = "testuser";

        // When
        String token = jwtUtils.generateJwtToken(username);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void getUsernameFromJwtToken_WithValidToken_ShouldReturnUsername() {
        // Given
        String username = "testuser";
        String token = jwtUtils.generateJwtToken(username);

        // When
        String extractedUsername = jwtUtils.getUsernameFromJwtToken(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateJwtToken_WithValidToken_ShouldReturnTrue() {
        // Given
        String token = jwtUtils.generateJwtToken("testuser");

        // When
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateJwtToken_WithInvalidToken_ShouldReturnFalse() {
        // Given
        String invalidToken = "invalid.jwt.token";

        // When
        boolean isValid = jwtUtils.validateJwtToken(invalidToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_WithEmptyToken_ShouldReturnFalse() {
        // Given
        String emptyToken = "";

        // When
        boolean isValid = jwtUtils.validateJwtToken(emptyToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_WithNullToken_ShouldReturnFalse() {
        // Given
        String nullToken = null;

        // When
        boolean isValid = jwtUtils.validateJwtToken(nullToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void isTokenExpired_WithValidToken_ShouldReturnFalse() {
        // Given
        String token = jwtUtils.generateJwtToken("testuser");

        // When
        boolean isExpired = jwtUtils.isTokenExpired(token);

        // Then
        assertFalse(isExpired);
    }

    @Test
    void validateJwtToken_WithMalformedToken_ShouldReturnFalse() {
        // Given
        String malformedToken = "malformed.token.here";

        // When
        boolean isValid = jwtUtils.validateJwtToken(malformedToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void getUsernameFromJwtToken_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.jwt.token";

        // When & Then
        assertThrows(Exception.class, () -> {
            jwtUtils.getUsernameFromJwtToken(invalidToken);
        });
    }

    @Test
    void tokenRoundTrip_ShouldPreserveUsername() {
        // Given
        String originalUsername = "testuser123";

        // When
        String token = jwtUtils.generateJwtToken(originalUsername);
        String extractedUsername = jwtUtils.getUsernameFromJwtToken(token);

        // Then
        assertEquals(originalUsername, extractedUsername);
        assertTrue(jwtUtils.validateJwtToken(token));
        assertFalse(jwtUtils.isTokenExpired(token));
    }

    @Test
    void generateJwtToken_WithSpecialCharactersInUsername_ShouldWork() {
        // Given
        String usernameWithSpecialChars = "test.user+123@example.com";

        // When
        String token = jwtUtils.generateJwtToken(usernameWithSpecialChars);
        String extractedUsername = jwtUtils.getUsernameFromJwtToken(token);

        // Then
        assertEquals(usernameWithSpecialChars, extractedUsername);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void validateJwtToken_WithTokenFromDifferentSecret_ShouldReturnFalse() {
        // Given - Create a different JWT utils with different secret
        JwtUtils differentSecretJwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(differentSecretJwtUtils, "jwtSecret", "differentSecret123456789012345678901234567890123456789012345678901234567890");
        ReflectionTestUtils.setField(differentSecretJwtUtils, "jwtExpirationMs", testExpirationMs);

        String token = differentSecretJwtUtils.generateJwtToken("testuser");

        // When
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Then
        assertFalse(isValid);
    }
}
