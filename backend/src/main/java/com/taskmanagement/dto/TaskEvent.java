package com.taskmanagement.dto;

/**
 * Represents a task event sent over WebSocket to clients.
 */
public class TaskEvent {
    private String action; // CREATED | UPDATED | DELETED
    private TaskResponse task;
    private Long taskId; // used for delete events when full task not available

    public TaskEvent() {}

    public TaskEvent(String action, TaskResponse task) {
        this.action = action;
        this.task = task;
        this.taskId = task != null ? task.getId() : null;
    }

    public TaskEvent(String action, Long taskId) {
        this.action = action;
        this.taskId = taskId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public TaskResponse getTask() {
        return task;
    }

    public void setTask(TaskResponse task) {
        this.task = task;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
