package com.tripsync.backend.repository;

import com.tripsync.backend.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
    List<Trip> findByOwner_IdOrderByStartDateAsc(UUID ownerId);
}

