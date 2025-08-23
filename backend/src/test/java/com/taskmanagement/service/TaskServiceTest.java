package com.taskmanagement.service;

import com.taskmanagement.dto.TaskCreateRequest;
import com.taskmanagement.dto.TaskUpdateRequest;
import com.taskmanagement.entity.Priority;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.exception.EntityNotFoundException;
import com.taskmanagement.exception.UnauthorizedException;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// no unused static imports
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private TaskCreateRequest createRequest;
    private TaskUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        createRequest = new TaskCreateRequest();
        createRequest.setTitle("T1");
        createRequest.setDescription("desc");
        createRequest.setStatus(TaskStatus.TODO);
        createRequest.setPriority(Priority.MEDIUM);
        createRequest.setAssigneeId(null);

        updateRequest = new TaskUpdateRequest();
        updateRequest.setTitle("T1-updated");
        updateRequest.setDescription("desc-updated");
        updateRequest.setStatus(TaskStatus.IN_PROGRESS);
        updateRequest.setPriority(Priority.HIGH);
        updateRequest.setAssigneeId(2L);
    }

    @Test
    void createTask_WhenCreatorNotFound_ShouldThrow() {
        when(userRepository.existsById(1L)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> taskService.createTask(createRequest, 1L));
        assertTrue(ex.getMessage().contains("Creator user not found"));
    }

    @Test
    void createTask_WhenAssigneeNotFound_ShouldThrow() {
        when(userRepository.existsById(1L)).thenReturn(true);
        createRequest.setAssigneeId(99L);
        when(userRepository.existsById(99L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> taskService.createTask(createRequest, 1L));
        assertTrue(ex.getMessage().contains("Assignee user not found"));
    }

    @Test
    void updateTask_WhenNotAuthorized_ShouldThrow() {
        Task existing = new Task();
        existing.setId(10L);
        existing.setCreatorId(1L);
        existing.setAssigneeId(3L);
        when(taskRepository.findById(10L)).thenReturn(Optional.of(existing));

        // user 2 is neither creator (1) nor assignee (3)
        UnauthorizedException ex = assertThrows(UnauthorizedException.class,
                () -> taskService.updateTask(10L, updateRequest, 2L));
        assertTrue(ex.getMessage().contains("Not authorized to update"));
    }

    @Test
    void updateTask_WhenAssigneeNotFound_ShouldThrow() {
        Task existing = new Task();
        existing.setId(11L);
        existing.setCreatorId(1L);
        existing.setAssigneeId(null);
        when(taskRepository.findById(11L)).thenReturn(Optional.of(existing));

    when(userRepository.existsById(2L)).thenReturn(false);

        updateRequest.setAssigneeId(2L);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> taskService.updateTask(11L, updateRequest, 1L));
        assertTrue(ex.getMessage().contains("Assignee user not found"));
    }

    @Test
    void deleteTask_WhenNotCreator_ShouldThrow() {
        Task existing = new Task();
        existing.setId(20L);
        existing.setCreatorId(1L);
        when(taskRepository.findById(20L)).thenReturn(Optional.of(existing));

        UnauthorizedException ex = assertThrows(UnauthorizedException.class,
                () -> taskService.deleteTask(20L, 2L));
        assertTrue(ex.getMessage().contains("Not authorized to delete"));
    }

    @Test
    void deleteTask_WhenCreator_ShouldDelete() {
        Task existing = new Task();
        existing.setId(21L);
        existing.setCreatorId(5L);
        when(taskRepository.findById(21L)).thenReturn(Optional.of(existing));

        taskService.deleteTask(21L, 5L);

        verify(taskRepository).deleteById(21L);
    }
}
