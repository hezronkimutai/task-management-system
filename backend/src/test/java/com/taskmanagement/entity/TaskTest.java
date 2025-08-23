package com.taskmanagement.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Task entity.
 */
class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
    }

    @Test
    void testTaskConstructor_Default() {
        // When
        Task newTask = new Task();

        // Then
        assertNotNull(newTask);
        assertEquals(TaskStatus.TODO, newTask.getStatus()); // Default status should be TODO
        assertEquals(Priority.MEDIUM, newTask.getPriority()); // Default priority should be MEDIUM
    }

    @Test
    void testTaskConstructor_WithParameters() {
        // Given
        String title = "Test Task";
        String description = "Test Description";
        TaskStatus status = TaskStatus.IN_PROGRESS;
        Priority priority = Priority.HIGH;
        Long assigneeId = 2L;
        Long creatorId = 1L;

        // When
        Task newTask = new Task(title, description, status, priority, assigneeId, creatorId);

        // Then
        assertEquals(title, newTask.getTitle());
        assertEquals(description, newTask.getDescription());
        assertEquals(status, newTask.getStatus());
        assertEquals(priority, newTask.getPriority());
        assertEquals(assigneeId, newTask.getAssigneeId());
        assertEquals(creatorId, newTask.getCreatorId());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Long id = 1L;
        String title = "Test Task";
        String description = "Test Description";
        TaskStatus status = TaskStatus.DONE;
        Priority priority = Priority.LOW;
        Long assigneeId = 2L;
        Long creatorId = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setAssigneeId(assigneeId);
        task.setCreatorId(creatorId);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);

        // Then
        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
        assertEquals(priority, task.getPriority());
        assertEquals(assigneeId, task.getAssigneeId());
        assertEquals(creatorId, task.getCreatorId());
        assertEquals(createdAt, task.getCreatedAt());
        assertEquals(updatedAt, task.getUpdatedAt());
    }

    @Test
    void testPrePersist() {
        // Given
        Task newTask = new Task();

        // When
        newTask.onCreate(); // Manually trigger @PrePersist method

        // Then
        assertNotNull(newTask.getCreatedAt());
        assertNotNull(newTask.getUpdatedAt());
    }

    @Test
    void testPreUpdate() {
        // Given
        Task newTask = new Task();
        newTask.onCreate(); // Set initial timestamps
        LocalDateTime originalCreatedAt = newTask.getCreatedAt();
        LocalDateTime originalUpdatedAt = newTask.getUpdatedAt();

        // Simulate some time passing
        try {
            Thread.sleep(1); // Small delay to ensure different timestamps
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // When
        newTask.onUpdate(); // Manually trigger @PreUpdate method

        // Then
        assertEquals(originalCreatedAt, newTask.getCreatedAt()); // Created timestamp should not change
        assertTrue(newTask.getUpdatedAt().isAfter(originalUpdatedAt)); // Updated timestamp should change
    }

    @Test
    void testToString() {
        // Given
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(Priority.MEDIUM);
        task.setAssigneeId(2L);
        task.setCreatorId(1L);

        // When
        String taskString = task.toString();

        // Then
        assertNotNull(taskString);
        assertTrue(taskString.contains("Test Task"));
        assertTrue(taskString.contains("Test Description"));
        assertTrue(taskString.contains("TODO"));
        assertTrue(taskString.contains("MEDIUM"));
    }

    @Test
    void testTaskWithNullValues() {
        // Given
        Task newTask = new Task();

        // When/Then - should not throw exceptions
        newTask.setAssigneeId(null);
        newTask.setDescription(null);

        assertNull(newTask.getAssigneeId());
        assertNull(newTask.getDescription());
    }

    @Test
    void testTaskStatusEnum() {
        // Test all TaskStatus values
        TaskStatus[] statuses = TaskStatus.values();
        assertEquals(3, statuses.length);
        
        assertTrue(java.util.Arrays.asList(statuses).contains(TaskStatus.TODO));
        assertTrue(java.util.Arrays.asList(statuses).contains(TaskStatus.IN_PROGRESS));
        assertTrue(java.util.Arrays.asList(statuses).contains(TaskStatus.DONE));
    }

    @Test
    void testPriorityEnum() {
        // Test all Priority values
        Priority[] priorities = Priority.values();
        assertEquals(3, priorities.length);
        
        assertTrue(java.util.Arrays.asList(priorities).contains(Priority.LOW));
        assertTrue(java.util.Arrays.asList(priorities).contains(Priority.MEDIUM));
        assertTrue(java.util.Arrays.asList(priorities).contains(Priority.HIGH));
    }
}
