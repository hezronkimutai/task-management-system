package com.taskmanagement.dto;

import com.taskmanagement.entity.ActivityType;

public class ActivityCreateRequest {
    private Long taskId;
    private ActivityType type;
    private Long actorId;
    private String actorName;
    private String detail;

    public ActivityCreateRequest() {}

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
}
