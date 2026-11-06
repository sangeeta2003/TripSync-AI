package com.tripsync.backend.model;


import com.tripsync.backend.models.enums.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "messages",
        indexes = @Index(
                name = "idx_messages_trip_sent",
                columnList = "trip_id, sent_at"
        )
)
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type",nullable = false,length = 20)
    private MessageType messageType = MessageType.TEXT;
    @CreationTimestamp
    @Column(name = "sent_at",nullable = false,updatable = false)
    private Instant sentAt;

    @Column(name = "edited_at")
    private Instant editedAt;
    @Column(name = "deleted_at")
    private Instant deletedAt;

}
