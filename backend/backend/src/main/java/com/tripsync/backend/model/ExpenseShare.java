package com.tripsync.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "expense_participants",
        uniqueConstraints = @UniqueConstraint(

                columnNames = {"expense_id","user_id"}

        )
)
@Getter
@Setter
@NoArgsConstructor
public class ExpenseShare {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private Expense expense;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private User user;
    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal sharedAmount;

}
