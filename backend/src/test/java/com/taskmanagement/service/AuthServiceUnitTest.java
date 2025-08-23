package com.taskmanagement.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void validatePassword_WhenMatches_ShouldReturnTrue() {
        when(passwordEncoder.matches("raw", "encoded")).thenReturn(true);
        boolean ok = userService.validatePassword("raw", "encoded");
        assertTrue(ok);
    }

    @Test
    void validatePassword_WhenDoesNotMatch_ShouldReturnFalse() {
        when(passwordEncoder.matches("raw", "encoded")).thenReturn(false);
        boolean ok = userService.validatePassword("raw", "encoded");
        assertFalse(ok);
    }
}
