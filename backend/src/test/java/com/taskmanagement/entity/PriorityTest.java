package com.taskmanagement.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Priority enum.
 */
class PriorityTest {

    @Test
    void testPriorityValues() {
        // Test all enum values exist
        Priority[] priorities = Priority.values();
        assertEquals(3, priorities.length);
        
        // Test specific values
        assertEquals("LOW", Priority.LOW.name());
        assertEquals("MEDIUM", Priority.MEDIUM.name());
        assertEquals("HIGH", Priority.HIGH.name());
    }

    @Test
    void testPriorityValueOf() {
        // Test valueOf method
        assertEquals(Priority.LOW, Priority.valueOf("LOW"));
        assertEquals(Priority.MEDIUM, Priority.valueOf("MEDIUM"));
        assertEquals(Priority.HIGH, Priority.valueOf("HIGH"));
    }

    @Test
    void testPriorityInvalidValue() {
        // Test invalid enum value throws exception
        assertThrows(IllegalArgumentException.class, () -> Priority.valueOf("INVALID"));
    }
}
