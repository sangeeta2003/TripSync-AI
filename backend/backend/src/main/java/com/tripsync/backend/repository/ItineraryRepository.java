package com.tripsync.backend.repository;

import com.tripsync.backend.model.ItineraryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItineraryRepository extends JpaRepository<ItineraryItem, UUID> {
    List<ItineraryItem> findByTrip_IdOrderByDayNumberAscSortOrderAsc(UUID tripId);

    Optional<ItineraryItem> findByIdAndTrip_Id(UUID id, UUID tripId);
}