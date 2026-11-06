package com.tripsync.backend.repository;

import com.tripsync.backend.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SettlementRepository extends JpaRepository<Settlement, UUID> {
    List<Settlement> findByTrip_IdOrderByCreatedAtDesc(UUID tripId);
}