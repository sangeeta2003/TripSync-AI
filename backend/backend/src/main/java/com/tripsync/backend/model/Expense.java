package com.tripsync.backend.model;


import com.tripsync.backend.models.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "paid_by",nullable = false)
    private User paidBy;
    @Column(nullable = false,length = 255)
    private String description;
    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;
    @Column(nullable = false,length = 3)
    private String currency = "INR";
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private ExpenseCategory expenseCategory = ExpenseCategory.OTHER;
    @Column(name = "expense_date",nullable = false)
    private Instant expenseDate = Instant.now();
    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;


}
