package com.tripsync.backend.model;



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
@Column( nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
    @Column(precision = 12,scale = 2)
    private BigDecimal budget;

    @Column(nullable = false,length = 3)
    private String currency ;

    @Enumerated(EnumType.STRING)
    private Domain.TripStatus status = Domain.TripStatus.PLANNING;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private User owner;


    @Column(nullable = false)
    private Instant createdAt = Instant.now();



}
