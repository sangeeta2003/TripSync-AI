package com.tripsync.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(
        name = "expense_participants",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_expense_participant",
                columnNames = {"expense_id","user_id"}

        )
)
@Getter
@Setter
@NoArgsConstructor
public class ExpenseParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "expense_id",nullable = false)
    private Expense expense;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @Column(name = "shared_amt",nullable = false,precision = 12,scale = 2)
    private BigDecimal sharedAmount;

}
