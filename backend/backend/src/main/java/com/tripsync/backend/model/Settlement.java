package com.tripsync.backend.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "settlements")
@Getter
@Setter
@NoArgsConstructor
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private User fromUser;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private User toUser;

    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;
    private Domain.SettlementStatus status = Domain.SettlementStatus.PENDING;



    private Instant createdAt = Instant.now();

}
