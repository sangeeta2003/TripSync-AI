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

@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private User sender;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "message_type",nullable = false,length = 20)
//    private MessageType messageType = MessageType.TEXT;
//
    private Instant createdAt = Instant.now();

}
