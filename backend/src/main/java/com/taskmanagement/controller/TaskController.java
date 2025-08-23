package com.taskmanagement.controller;

import com.taskmanagement.dto.TaskCreateRequest;
import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.dto.TaskUpdateRequest;
import com.taskmanagement.entity.Task;
import com.taskmanagement.exception.EntityNotFoundException;
import com.taskmanagement.exception.UnauthorizedException;
import com.taskmanagement.service.TaskService;
import com.taskmanagement.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Task controller for CRUD operations on tasks.
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
@Tag(name = "Tasks", description = "Task management endpoints for creating, reading, updating, and deleting tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Get current authenticated user ID from security context.
     *
     * @return the current user ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsServiceImpl.UserPrincipal) {
            UserDetailsServiceImpl.UserPrincipal userPrincipal = (UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getId();
        }
        throw new UnauthorizedException("User not authenticated");
    }

    /**
     * Get all tasks.
     *
     * @return list of all tasks
     */
    @Operation(
            summary = "Get all tasks",
            description = "Retrieve a list of all tasks in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tasks retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "error": "Authentication required"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskResponse> taskResponses = tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResponses);
    }

    /**
     * Get task by ID.
     *
     * @param id the task ID
     * @return the task details
     */
    @Operation(
            summary = "Get task by ID",
            description = "Retrieve a specific task by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "error": "Task not found with ID: 1"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TaskResponse> getTaskById(@Parameter(description = "Task ID") @PathVariable Long id) {
        Task task = taskService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        return ResponseEntity.ok(new TaskResponse(task));
    }

    /**
     * Create a new task.
     *
     * @param taskRequest the task creation request
     * @return the created task
     */
    @Operation(
            summary = "Create a new task",
            description = "Create a new task with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "title": "Implement user authentication",
                                        "description": "Implement JWT-based authentication for the application",
                                        "status": "TODO",
                                        "priority": "HIGH",
                                        "assigneeId": 2,
                                        "creatorId": 1,
                                        "createdAt": "2023-01-01T10:00:00",
                                        "updatedAt": "2023-01-01T10:00:00"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid task data",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "error": "Title is required"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required"
            )
    })
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest taskRequest) {
        Long currentUserId = getCurrentUserId();
        Task createdTask = taskService.createTask(taskRequest, currentUserId);
        return ResponseEntity.ok(new TaskResponse(createdTask));
    }

    /**
     * Update an existing task.
     *
     * @param id the task ID
     * @param taskRequest the task update request
     * @return the updated task
     */
    @Operation(
            summary = "Update an existing task",
            description = "Update an existing task with the provided details. Only the creator or assignee can update a task."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "error": "Task not found with ID: 1"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Not authorized to update this task",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "error": "Not authorized to update this task"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TaskResponse> updateTask(
            @Parameter(description = "Task ID") @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest taskRequest) {
        Long currentUserId = getCurrentUserId();
        Task updatedTask = taskService.updateTask(id, taskRequest, currentUserId);
        return ResponseEntity.ok(new TaskResponse(updatedTask));
    }

    /**
     * Delete a task.
     *
     * @param id the task ID
     * @return confirmation of deletion
     */
    @Operation(
            summary = "Delete a task",
            description = "Delete an existing task. Only the creator can delete a task."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "message": "Task deleted successfully"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "error": "Task not found with ID: 1"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Not authorized to delete this task",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "error": "Not authorized to delete this task"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteTask(@Parameter(description = "Task ID") @PathVariable Long id) {
        Long currentUserId = getCurrentUserId();
        taskService.deleteTask(id, currentUserId);
        return ResponseEntity.ok("{\"message\": \"Task deleted successfully\"}");
    }
}
