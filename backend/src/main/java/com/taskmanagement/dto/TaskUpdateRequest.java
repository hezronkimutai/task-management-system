package com.taskmanagement.dto;

import com.taskmanagement.entity.Priority;
import com.taskmanagement.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for updating an existing task.
 */
@Schema(description = "Request for updating an existing task")
public class TaskUpdateRequest {

    @Schema(description = "Task title", example = "Implement user authentication", required = true)
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @Schema(description = "Task description", example = "Implement JWT-based authentication for the application")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Schema(description = "Task status", example = "IN_PROGRESS", required = true)
    @NotNull(message = "Status is required")
    private TaskStatus status;

    @Schema(description = "Task priority", example = "HIGH", required = true)
    @NotNull(message = "Priority is required")
    private Priority priority;

    @Schema(description = "ID of the user assigned to this task", example = "2")
    private Long assigneeId;

    @Schema(description = "Optional due date/time for the task (ISO-8601)", example = "2025-08-25T15:00:00")
    private java.time.LocalDateTime dueDate;

    // Constructors
    public TaskUpdateRequest() {}

    public TaskUpdateRequest(String title, String description, TaskStatus status, Priority priority, Long assigneeId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assigneeId = assigneeId;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public java.time.LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(java.time.LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
