package com.tripsync.backend.model;


import com.tripsync.backend.models.enums.TripStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,length = 150)
    private String title;
    @Column(nullable = false,length = 200)
    private String destination;
@Column(name = "start_date", nullable = false)
    private LocalDate startdate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(precision = 12,scale = 2)
    private BigDecimal budget;

    @Column(nullable = false,length = 3)
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private TripStatus status = TripStatus.PLANNING;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;



}
