package com.tripsync.backend.repository;

import com.tripsync.backend.model.TripMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public  interface TripMemberRepository extends JpaRepository<TripMember, UUID> {
    Optional<TripMember> findByTrip_IdAndUser_Id(UUID tripId, UUID userId);

    List<TripMember> findByTrip_IdAndStatus(UUID tripId, String status);

    List<TripMember> findByUser_IdAndStatus(UUID userId, String status);

    boolean existsByTrip_IdAndUser_IdAndStatus(UUID tripId, UUID userId, String status);
}
