package com.taskmanagement.dto;

import com.taskmanagement.entity.Priority;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO for task response.
 */
@Schema(description = "Task response containing task details")
public class TaskResponse {

    @Schema(description = "Task ID", example = "1")
    private Long id;

    @Schema(description = "Task title", example = "Implement user authentication")
    private String title;

    @Schema(description = "Task description", example = "Implement JWT-based authentication for the application")
    private String description;

    @Schema(description = "Task status", example = "TODO")
    private TaskStatus status;

    @Schema(description = "Task priority", example = "HIGH")
    private Priority priority;

    @Schema(description = "ID of the user assigned to this task", example = "2")
    private Long assigneeId;

    @Schema(description = "ID of the user who created this task", example = "1")
    private Long creatorId;

    @Schema(description = "Task creation timestamp", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Task last update timestamp", example = "2023-01-01T10:30:00")
    private LocalDateTime updatedAt;

    // Constructors
    public TaskResponse() {}

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.assigneeId = task.getAssigneeId();
        this.creatorId = task.getCreatorId();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }

    public TaskResponse(Long id, String title, String description, TaskStatus status, Priority priority, 
                       Long assigneeId, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assigneeId = assigneeId;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
