package com.taskmanagement.dto;

import java.time.LocalDateTime;

/**
 * DTO for sending notification messages to clients (via websocket).
 */
public class NotificationEvent {
    private String type; // e.g. DUE_SOON, OVERDUE
    private Long taskId;
    private String title;
    private Long assigneeId;
    private LocalDateTime dueDate;

    public NotificationEvent() {}

    public NotificationEvent(String type, Long taskId, String title, Long assigneeId, LocalDateTime dueDate) {
        this.type = type;
        this.taskId = taskId;
        this.title = title;
        this.assigneeId = assigneeId;
        this.dueDate = dueDate;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
}
