package com.tripsync.backend.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class AppNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private User user;



    @Enumerated(EnumType.STRING)

    private Domain.NotificationType type;
    private String title;

    @Column(columnDefinition = "text")
    private String body;

    private boolean readFlag = false;

    private Instant createdAt = Instant.now();



}
