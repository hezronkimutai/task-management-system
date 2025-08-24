package com.taskmanagement.dto;

import com.taskmanagement.entity.Activity;
import com.taskmanagement.entity.ActivityType;

import java.time.LocalDateTime;

public class ActivityResponse {
    private Long id;
    private Long taskId;
    private ActivityType type;
    private Long actorId;
    private String actorName;
    private String detail;
    private LocalDateTime createdAt;

    public ActivityResponse() {}

    public ActivityResponse(Activity activity) {
        this.id = activity.getId();
        this.taskId = activity.getTaskId();
        this.type = activity.getType();
        this.actorId = activity.getActorId();
        this.actorName = activity.getActorName();
        this.detail = activity.getDetail();
        this.createdAt = activity.getCreatedAt();
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { this.type = type; }

    public Long getActorId() { return actorId; }
    public void setActorId(Long actorId) { this.actorId = actorId; }

    public String getActorName() { return actorName; }
    public void setActorName(String actorName) { this.actorName = actorName; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
