package com.taskmanagement.service;

import com.taskmanagement.dto.TaskCreateRequest;
import com.taskmanagement.dto.TaskUpdateRequest;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.entity.Priority;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Task entity operations.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new task.
     *
     * @param taskRequest the task creation request
     * @param creatorId the ID of the user creating the task
     * @return the created task
     */
    public Task createTask(TaskCreateRequest taskRequest, Long creatorId) {
        // Validate that creator exists
        if (!userRepository.existsById(creatorId)) {
            throw new RuntimeException("Creator user not found with ID: " + creatorId);
        }

        // Validate assignee if provided
        if (taskRequest.getAssigneeId() != null && !userRepository.existsById(taskRequest.getAssigneeId())) {
            throw new RuntimeException("Assignee user not found with ID: " + taskRequest.getAssigneeId());
        }

        Task task = new Task(
                taskRequest.getTitle(),
                taskRequest.getDescription(),
                taskRequest.getStatus(),
                taskRequest.getPriority(),
                taskRequest.getAssigneeId(),
                creatorId
        );

        return taskRepository.save(task);
    }

    /**
     * Update an existing task.
     *
     * @param taskId the ID of the task to update
     * @param taskRequest the task update request
     * @param userId the ID of the user updating the task
     * @return the updated task
     * @throws RuntimeException if task not found or user not authorized
     */
    public Task updateTask(Long taskId, TaskUpdateRequest taskRequest, Long userId) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        // Check authorization: only creator or assignee can update
        if (!existingTask.getCreatorId().equals(userId) && 
            (existingTask.getAssigneeId() == null || !existingTask.getAssigneeId().equals(userId))) {
            throw new RuntimeException("Not authorized to update this task");
        }

        // Validate assignee if provided
        if (taskRequest.getAssigneeId() != null && !userRepository.existsById(taskRequest.getAssigneeId())) {
            throw new RuntimeException("Assignee user not found with ID: " + taskRequest.getAssigneeId());
        }

        // Update task fields
        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStatus(taskRequest.getStatus());
        existingTask.setPriority(taskRequest.getPriority());
        existingTask.setAssigneeId(taskRequest.getAssigneeId());

        return taskRepository.save(existingTask);
    }

    /**
     * Get all tasks.
     *
     * @return list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Find task by ID.
     *
     * @param id the task ID
     * @return Optional containing the task if found
     */
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Get tasks related to a user (created or assigned).
     *
     * @param userId the user ID
     * @return list of tasks related to the user
     */
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findTasksByUserId(userId);
    }

    /**
     * Get tasks created by a user.
     *
     * @param creatorId the creator ID
     * @return list of tasks created by the user
     */
    public List<Task> getTasksByCreatorId(Long creatorId) {
        return taskRepository.findByCreatorId(creatorId);
    }

    /**
     * Get tasks assigned to a user.
     *
     * @param assigneeId the assignee ID
     * @return list of tasks assigned to the user
     */
    public List<Task> getTasksByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    /**
     * Get tasks by status.
     *
     * @param status the task status
     * @return list of tasks with the specified status
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Get tasks by priority.
     *
     * @param priority the task priority
     * @return list of tasks with the specified priority
     */
    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    /**
     * Delete a task.
     *
     * @param taskId the ID of the task to delete
     * @param userId the ID of the user deleting the task
     * @throws RuntimeException if task not found or user not authorized
     */
    public void deleteTask(Long taskId, Long userId) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        // Check authorization: only creator can delete
        if (!existingTask.getCreatorId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this task");
        }

        taskRepository.deleteById(taskId);
    }

    /**
     * Check if task exists.
     *
     * @param id the task ID
     * @return true if exists, false otherwise
     */
    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }

    /**
     * Count tasks by status.
     *
     * @param status the status to count
     * @return number of tasks with the specified status
     */
    public long countByStatus(TaskStatus status) {
        return taskRepository.countByStatus(status);
    }

    /**
     * Count tasks created by a user.
     *
     * @param creatorId the creator ID
     * @return number of tasks created by the user
     */
    public long countByCreatorId(Long creatorId) {
        return taskRepository.countByCreatorId(creatorId);
    }

    /**
     * Count tasks assigned to a user.
     *
     * @param assigneeId the assignee ID
     * @return number of tasks assigned to the user
     */
    public long countByAssigneeId(Long assigneeId) {
        return taskRepository.countByAssigneeId(assigneeId);
    }
}
