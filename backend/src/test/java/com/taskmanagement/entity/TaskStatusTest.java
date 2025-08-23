package com.taskmanagement.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TaskStatus enum.
 */
class TaskStatusTest {

    @Test
    void testTaskStatusValues() {
        // Test all enum values exist
        TaskStatus[] statuses = TaskStatus.values();
        assertEquals(3, statuses.length);
        
        // Test specific values
        assertEquals("TODO", TaskStatus.TODO.name());
        assertEquals("IN_PROGRESS", TaskStatus.IN_PROGRESS.name());
        assertEquals("DONE", TaskStatus.DONE.name());
    }

    @Test
    void testTaskStatusValueOf() {
        // Test valueOf method
        assertEquals(TaskStatus.TODO, TaskStatus.valueOf("TODO"));
        assertEquals(TaskStatus.IN_PROGRESS, TaskStatus.valueOf("IN_PROGRESS"));
        assertEquals(TaskStatus.DONE, TaskStatus.valueOf("DONE"));
    }

    @Test
    void testTaskStatusInvalidValue() {
        // Test invalid enum value throws exception
        assertThrows(IllegalArgumentException.class, () -> TaskStatus.valueOf("INVALID"));
    }
}
