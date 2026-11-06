package com.tripsync.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "itinerary_items")
@Getter
@Setter
@NoArgsConstructor
public class ItineraryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    private Trip trip;

    private int dayNumber;
    @Column(nullable = false,length = 200)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String location;
    private LocalTime startTime;
    @Column(precision = 12,scale = 2)
    private BigDecimal estimatedCost ;

    private int sortOrder ;


}
