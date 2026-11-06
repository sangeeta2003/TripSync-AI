package com.tripsync.backend.service;

import com.tripsync.backend.repository.TripMemberRepository;
import com.tripsync.backend.repository.TripRepository;
import org.springframework.stereotype.Service;
import com.tripsync.backend.model.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TripAccess {
    private final TripRepository tripRepository;
    private final TripMemberRepository tripMemberRepository;
    public TripAccess(TripRepository tripRepository,TripMemberRepository tripMemberRepository){
        this.tripRepository = tripRepository;
        this.tripMemberRepository = tripMemberRepository;
    }
    public Trip get(UUID id){
        return tripRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Trip not found"));
    }
    public  void member(UUID t,UUID u){
        if(!tripMemberRepository.existsByTrip_IdAndUser_IdAndStatus(t,u,"ACTIVE"))
            throw new SecurityException("Trip membership required");
    }
    public void admin(UUID t, UUID u){
        var m = tripMemberRepository.findByTrip_IdAndUser_Id(t,u)
                .orElseThrow(() -> new SecurityException("Trip membership required"));
        if(!"ACTIVE".equals(m.getStatus())|| (m.getRole() != Domain.Role.OWNER && m.getRole() != Domain.Role.ADMIN))
            throw new SecurityException("Owner/admin role required");
    }
}
