package com.tripsync.backend.repository;

import com.tripsync.backend.model.ChatMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage>  findTop100ByTrip_IdOrderBySentAtAsc(UUID tripId);
}
