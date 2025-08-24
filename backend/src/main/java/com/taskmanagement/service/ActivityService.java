package com.taskmanagement.service;

import com.taskmanagement.dto.ActivityCreateRequest;
import com.taskmanagement.entity.Activity;
import com.taskmanagement.entity.ActivityType;
import com.taskmanagement.exception.EntityNotFoundException;
import com.taskmanagement.repository.ActivityRepository;
import com.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Activity createActivity(ActivityCreateRequest req) {
        // validate task exists
        if (req.getTaskId() == null || !taskRepository.existsById(req.getTaskId())) {
            throw new EntityNotFoundException("Task not found with ID: " + req.getTaskId());
        }

        Activity a = new Activity(req.getTaskId(), req.getType() != null ? req.getType() : ActivityType.UPDATED, req.getActorId(), req.getActorName(), req.getDetail());
        return activityRepository.save(a);
    }

    public List<Activity> getActivitiesForTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found with ID: " + taskId);
        }
        return activityRepository.findByTaskIdOrderByCreatedAtAsc(taskId);
    }
}
