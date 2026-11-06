package com.tripsync.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "trip_members",
        uniqueConstraints=
                @UniqueConstraint(
                        name = "uk_trip_member",
                        columnNames = {"trip_id","user_id"}

)

)
@Getter
@Setter
@NoArgsConstructor
public class TripMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)

    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)

private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
   private Domain.Role role;
    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";



    @Column(nullable = false)
    private Instant joinedAt = Instant.now();


}
