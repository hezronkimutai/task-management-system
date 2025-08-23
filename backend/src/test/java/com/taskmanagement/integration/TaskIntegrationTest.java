package com.taskmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.RegisterRequest;
import com.taskmanagement.dto.TaskCreateRequest;
import com.taskmanagement.dto.TaskUpdateRequest;
import com.taskmanagement.entity.Priority;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebMvc
@Transactional
class TaskIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    private String registerAndLogin(String username, String email, String password) throws Exception {
        RegisterRequest reg = new RegisterRequest();
        reg.setUsername(username);
        reg.setEmail(email);
        reg.setPassword(password);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        LoginRequest login = new LoginRequest();
        login.setUsername(username);
        login.setPassword(password);

        String resp = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Extract token
        return objectMapper.readTree(resp).get("token").asText();
    }

    @Test
    void endToEnd_TaskCrudAndAssignment_StatusTransitions() throws Exception {
        // Register two users
        String token1 = registerAndLogin("creator", "creator@example.com", "password1");
        String token2 = registerAndLogin("assignee", "assignee@example.com", "password2");

        // Create a task as creator
        TaskCreateRequest createReq = new TaskCreateRequest();
        createReq.setTitle("Implement feature");
        createReq.setDescription("Details");
        createReq.setStatus(TaskStatus.TODO);
        createReq.setPriority(Priority.HIGH);

        String createResp = mockMvc.perform(post("/api/tasks")
                .header("Authorization", "Bearer " + token1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        Long taskId = objectMapper.readTree(createResp).get("id").asLong();

        // Assign to assignee and change status to IN_PROGRESS
        TaskUpdateRequest updateReq = new TaskUpdateRequest();
        updateReq.setTitle("Implement feature");
        updateReq.setDescription("Details updated");
        updateReq.setAssigneeId(userRepository.findByUsername("assignee").get().getId());
        updateReq.setStatus(TaskStatus.IN_PROGRESS);
        updateReq.setPriority(Priority.HIGH);

        mockMvc.perform(put("/api/tasks/" + taskId)
                .header("Authorization", "Bearer " + token1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assigneeId").value(updateReq.getAssigneeId()))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        // Ensure assignee can update the status to DONE
        TaskUpdateRequest assigneeUpdate = new TaskUpdateRequest();
        assigneeUpdate.setTitle("Implement feature");
        assigneeUpdate.setDescription("Completed");
        assigneeUpdate.setAssigneeId(updateReq.getAssigneeId());
        assigneeUpdate.setStatus(TaskStatus.DONE);
        assigneeUpdate.setPriority(Priority.HIGH);

        mockMvc.perform(put("/api/tasks/" + taskId)
                .header("Authorization", "Bearer " + token2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assigneeUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));

        // Delete task as creator
        mockMvc.perform(delete("/api/tasks/" + taskId)
                .header("Authorization", "Bearer " + token1))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Task deleted successfully")));
    }
}
