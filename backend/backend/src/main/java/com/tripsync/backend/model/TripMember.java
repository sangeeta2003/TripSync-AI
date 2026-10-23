package com.tripsync.backend.model;

import com.tripsync.backend.models.enums.TripMemberRole;
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
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private TripMemberRole rolw = TripMemberRole.MEMBER;

    @CreationTimestamp
    @Column(name = "joined_at",nullable = false,updatable = false)
    private Instant joinedAt;


}
