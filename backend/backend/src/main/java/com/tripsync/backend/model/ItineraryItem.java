package com.tripsync.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;
    @Column(name = "day_number",nullable = false)
    private Integer dayNumber;
    @Column(nullable = false,length = 200)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "estimated_cost",precision = 12,scale = 2)
    private BigDecimal estimatedCost = BigDecimal.ZERO;
    @Column(name = "sort_order",nullable = false)
    private Integer sortOrder = 0;


}
