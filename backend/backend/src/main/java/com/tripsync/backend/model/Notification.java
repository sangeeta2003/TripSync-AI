package com.tripsync.backend.model;


import com.tripsync.backend.models.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "notification",
        indexes = {
                @Index(
        name = "idx_notifications_user_read",
        columnList = "user_id , is_read"
),
@Index(
        name = "idx_notifications_trip",
        columnList = "trip_id"
)
}
)
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private NotificationType notificationType = NotificationType.NEW_MESSAGE;
    @Column(nullable = false,length = 255)
    private String title;

    @Column(length = 1000)
    private String body;
    @Column(name = "is_read",nullable = false)
    private boolean read = false;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;



}
