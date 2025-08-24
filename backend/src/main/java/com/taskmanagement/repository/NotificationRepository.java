package com.taskmanagement.repository;

import com.taskmanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdOrderByCreatedAtDesc(Long recipientId);
    // include broadcast notifications (recipientId IS NULL) and per-user ones
    List<Notification> findByRecipientIdOrRecipientIdIsNullOrderByCreatedAtDesc(Long recipientId);
}
