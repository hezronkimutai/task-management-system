package com.taskmanagement.repository;

import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task entity operations.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Find tasks by creator ID.
     *
     * @param creatorId the creator ID to search for
     * @return List of tasks created by the user
     */
    List<Task> findByCreatorId(Long creatorId);
    
    /**
     * Find tasks by assignee ID.
     *
     * @param assigneeId the assignee ID to search for
     * @return List of tasks assigned to the user
     */
    List<Task> findByAssigneeId(Long assigneeId);
    
    /**
     * Find tasks by status.
     *
     * @param status the status to search for
     * @return List of tasks with the specified status
     */
    List<Task> findByStatus(TaskStatus status);
    
    /**
     * Find tasks by priority.
     *
     * @param priority the priority to search for
     * @return List of tasks with the specified priority
     */
    List<Task> findByPriority(Priority priority);
    
    /**
     * Find tasks by creator ID and status.
     *
     * @param creatorId the creator ID
     * @param status the task status
     * @return List of tasks matching the criteria
     */
    List<Task> findByCreatorIdAndStatus(Long creatorId, TaskStatus status);
    
    /**
     * Find tasks by assignee ID and status.
     *
     * @param assigneeId the assignee ID
     * @param status the task status
     * @return List of tasks matching the criteria
     */
    List<Task> findByAssigneeIdAndStatus(Long assigneeId, TaskStatus status);
    
    /**
     * Find tasks where user is either creator or assignee.
     *
     * @param userId the user ID
     * @return List of tasks related to the user
     */
    @Query("SELECT t FROM Task t WHERE t.creatorId = :userId OR t.assigneeId = :userId")
    List<Task> findTasksByUserId(@Param("userId") Long userId);
    
    /**
     * Count tasks by status.
     *
     * @param status the status to count
     * @return Number of tasks with the specified status
     */
    long countByStatus(TaskStatus status);
    
    /**
     * Count tasks by creator ID.
     *
     * @param creatorId the creator ID
     * @return Number of tasks created by the user
     */
    long countByCreatorId(Long creatorId);
    
    /**
     * Count tasks by assignee ID.
     *
     * @param assigneeId the assignee ID
     * @return Number of tasks assigned to the user
     */
    long countByAssigneeId(Long assigneeId);
}
