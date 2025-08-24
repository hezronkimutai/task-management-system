package com.taskmanagement.service;

import com.taskmanagement.entity.Notification;
import com.taskmanagement.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification save(Notification n) {
        return notificationRepository.save(n);
    }

    public List<Notification> findForUser(Long userId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
    }

    public void markRead(Long id) {
        notificationRepository.findById(id).ifPresent(n -> { n.setReadFlag(true); notificationRepository.save(n); });
    }
}
