package com.tripsync.backend.repository;

import com.tripsync.backend.model.AppNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<AppNotification, UUID> {
    List<AppNotification> findTop100ByUser_IdOrderByCreatedAtDesc(UUID userId);
}
