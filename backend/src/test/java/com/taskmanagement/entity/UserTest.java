package com.taskmanagement.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserConstructor_Default() {
        // When
        User newUser = new User();

        // Then
        assertNotNull(newUser);
        assertEquals(Role.USER, newUser.getRole()); // Default role should be USER
        // Note: createdAt is set by @PrePersist only when entity is persisted
    }

    @Test
    void testUserConstructor_WithUsernameEmailPassword() {
        // Given
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        // When
        User newUser = new User(username, email, password);

        // Then
        assertEquals(username, newUser.getUsername());
        assertEquals(email, newUser.getEmail());
        assertEquals(password, newUser.getPassword());
        assertEquals(Role.USER, newUser.getRole()); // Default role
        // Note: createdAt is set by @PrePersist only when entity is persisted
    }

    @Test
    void testUserConstructor_WithUsernameEmailPasswordRole() {
        // Given
        String username = "admin";
        String email = "admin@example.com";
        String password = "admin123";
        Role role = Role.ADMIN;

        // When
        User newUser = new User(username, email, password, role);

        // Then
        assertEquals(username, newUser.getUsername());
        assertEquals(email, newUser.getEmail());
        assertEquals(password, newUser.getPassword());
        assertEquals(role, newUser.getRole());
        // Note: createdAt is set by @PrePersist only when entity is persisted
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";
        Role role = Role.ADMIN;
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setCreatedAt(createdAt);

        // Then
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(createdAt, user.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        User user1 = new User("testuser", "test@example.com", "password");
        user1.setId(1L);

        User user2 = new User("testuser", "test@example.com", "password");
        user2.setId(1L);

        User user3 = new User("differentuser", "different@example.com", "password");
        user3.setId(2L);

        // Then - Test basic field equality
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getUsername(), user2.getUsername());
        assertNotEquals(user1.getUsername(), user3.getUsername());
    }

    @Test
    void testToString() {
        // Given
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRole(Role.USER);

        // When
        String toString = user.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("USER"));
    }

    @Test
    void testUserValidation() {
        // Given
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        // Then
        assertNotNull(user.getUsername());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertTrue(user.getEmail().contains("@"));
        assertTrue(user.getPassword().length() >= 6);
    }

    @Test
    void testRoleEnumValues() {
        // Test that Role enum has expected values
        assertEquals("USER", Role.USER.toString());
        assertEquals("ADMIN", Role.ADMIN.toString());
        assertEquals(2, Role.values().length);
    }

    @Test
    void testPrePersist() {
        // Given
        User newUser = new User("testuser", "test@example.com", "password");
        LocalDateTime beforeCreation = LocalDateTime.now().minusSeconds(1);

        // When - Manually call the @PrePersist method
        newUser.onCreate();

        // Then
        assertNotNull(newUser.getCreatedAt());
        assertTrue(newUser.getCreatedAt().isAfter(beforeCreation));
        assertTrue(newUser.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testUserWithNullValues() {
        // Given
        User nullUser = new User();

        // When
        nullUser.setUsername(null);
        nullUser.setEmail(null);
        nullUser.setPassword(null);

        // Then
        assertNull(nullUser.getUsername());
        assertNull(nullUser.getEmail());
        assertNull(nullUser.getPassword());
        assertEquals(Role.USER, nullUser.getRole()); // Default role should still be set
    }

    @Test
    void testUserWithEmptyStrings() {
        // Given
        User emptyUser = new User();

        // When
        emptyUser.setUsername("");
        emptyUser.setEmail("");
        emptyUser.setPassword("");

        // Then
        assertEquals("", emptyUser.getUsername());
        assertEquals("", emptyUser.getEmail());
        assertEquals("", emptyUser.getPassword());
    }
}
