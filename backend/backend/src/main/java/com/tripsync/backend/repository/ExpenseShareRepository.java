package com.tripsync.backend.repository;

import com.tripsync.backend.model.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {
    List<ExpenseShare> findByExpense_Id(UUID expenseId);
}
