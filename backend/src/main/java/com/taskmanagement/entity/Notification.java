package com.taskmanagement.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Long taskId;

    private String title;

    private Long assigneeId;

    // recipientId: when set, this notification is intended for a single user
    private Long recipientId;

    private boolean readFlag = false;

    private LocalDateTime dueDate;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {}

    public Notification(String type, Long taskId, String title, Long assigneeId, Long recipientId, LocalDateTime dueDate) {
        this.type = type;
        this.taskId = taskId;
        this.title = title;
        this.assigneeId = assigneeId;
        this.recipientId = recipientId;
        this.dueDate = dueDate;
        this.readFlag = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }

    public Long getRecipientId() { return recipientId; }
    public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }

    public boolean isReadFlag() { return readFlag; }
    public void setReadFlag(boolean readFlag) { this.readFlag = readFlag; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
