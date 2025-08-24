package com.taskmanagement.service;

import com.taskmanagement.dto.NotificationEvent;
import com.taskmanagement.entity.Task;
import com.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Periodically checks for tasks with due dates approaching or overdue and sends notifications.
 */
@Service
public class ScheduledNotificationService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private com.taskmanagement.service.NotificationService notificationService;

    // Run every minute to find tasks due within the next hour or already overdue
    @Scheduled(fixedRate = 60_000)
    public void checkDueTasks() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inOneHour = now.plusHours(1);
        // find all tasks with non-null dueDate between now and inOneHour, or before now (overdue)
        List<Task> all = taskRepository.findAll();
        for (Task t : all) {
            LocalDateTime due = t.getDueDate();
            if (due == null) continue;
            try {
                if (due.isBefore(now)) {
                    // overdue
                    NotificationEvent ev = new NotificationEvent("OVERDUE", t.getId(), t.getTitle(), t.getAssigneeId(), due);
                    if (t.getAssigneeId() != null) {
                        try { messagingTemplate.convertAndSendToUser(String.valueOf(t.getAssigneeId()), "/queue/notifications", ev); } catch (Exception ignored) {}
                        notificationService.save(new com.taskmanagement.entity.Notification(ev.getType(), ev.getTaskId(), ev.getTitle(), ev.getAssigneeId(), ev.getAssigneeId(), ev.getDueDate()));
                    } else {
                        try { messagingTemplate.convertAndSend("/topic/notifications", ev); } catch (Exception ignored) {}
                        notificationService.save(new com.taskmanagement.entity.Notification(ev.getType(), ev.getTaskId(), ev.getTitle(), ev.getAssigneeId(), null, ev.getDueDate()));
                    }
                } else if (!due.isAfter(inOneHour)) {
                    // due within next hour
                    NotificationEvent ev = new NotificationEvent("DUE_SOON", t.getId(), t.getTitle(), t.getAssigneeId(), due);
                    if (t.getAssigneeId() != null) {
                        try { messagingTemplate.convertAndSendToUser(String.valueOf(t.getAssigneeId()), "/queue/notifications", ev); } catch (Exception ignored) {}
                        notificationService.save(new com.taskmanagement.entity.Notification(ev.getType(), ev.getTaskId(), ev.getTitle(), ev.getAssigneeId(), ev.getAssigneeId(), ev.getDueDate()));
                    } else {
                        try { messagingTemplate.convertAndSend("/topic/notifications", ev); } catch (Exception ignored) {}
                        notificationService.save(new com.taskmanagement.entity.Notification(ev.getType(), ev.getTaskId(), ev.getTitle(), ev.getAssigneeId(), null, ev.getDueDate()));
                    }
                }
            } catch (Exception ignored) {}
        }
    }
}
