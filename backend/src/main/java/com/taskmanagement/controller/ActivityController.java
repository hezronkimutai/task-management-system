package com.taskmanagement.controller;

import com.taskmanagement.dto.ActivityCreateRequest;
import com.taskmanagement.dto.ActivityResponse;
import com.taskmanagement.entity.Activity;
import com.taskmanagement.service.ActivityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
@Tag(name = "Activities", description = "Task activity logs")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ActivityResponse> createActivity(@Valid @RequestBody ActivityCreateRequest req) {
        Activity created = activityService.createActivity(req);
        return ResponseEntity.ok(new ActivityResponse(created));
    }

    @GetMapping("/task/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ActivityResponse>> getForTask(@PathVariable Long taskId) {
        List<Activity> activities = activityService.getActivitiesForTask(taskId);
        List<ActivityResponse> resp = activities.stream().map(ActivityResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }
}
