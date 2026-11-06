package com.tripsync.backend.repository;

import com.tripsync.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByTrip_IdOrderByCreatedAtDesc(UUID tripId);
}
