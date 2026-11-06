package com.tripsync.backend.service;

import com.tripsync.backend.dto.Requests;
import com.tripsync.backend.repository.*;
import com.tripsync.backend.security.JwtService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tripsync.backend.model.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;



@Service
public class CoreService {
    private  final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final TripMemberRepository tripMemberRepository;
    private final ItineraryRepository itineraryRepository;
    private final ExpenseRepository expenseRepository;
    private  final ExpenseShareRepository expenseShareRepository;
    private  final SettlementRepository settlementRepository;
    private final CurrentUser currentUser;
    private final TripAccess tripAccess;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public CoreService(UserRepository userRepository,TripRepository tripRepository,TripMemberRepository tripMemberRepository,ItineraryRepository itineraryRepository,ExpenseRepository expenseRepository,ExpenseShareRepository expenseShareRepository,SettlementRepository settlementRepository,CurrentUser currentUser,PasswordEncoder passwordEncoder,JwtService jwtService,TripAccess tripAccess){
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.tripMemberRepository = tripMemberRepository;
        this.itineraryRepository = itineraryRepository;
        this.expenseRepository = expenseRepository;
        this.expenseShareRepository = expenseShareRepository;
        this.settlementRepository = settlementRepository;
        this.currentUser = currentUser;
        this.tripAccess = tripAccess;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }
    public Map<String,Object> signUp(Requests.Signup r){
        if(userRepository.existsByEmailIgnoreCase(r.email()))
            throw new IllegalArgumentException("Email already registered");
        User u = userRepository.save(new User(r.name(),r.email().trim(),passwordEncoder.encode(r.password())));
        return auth(u);

    }
    public Map<String,Object> login(Requests.Login r){
        User u = userRepository.findByEmailIgnoreCase(r.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if(!passwordEncoder.matches(r.password(), u.getPasswordHash()))
            throw new IllegalArgumentException("Invalid email or password");
        return auth(u);
    }
    private Map<String,Object>auth(User u){
        return Map.of("accessToken",jwtService.create(u.getEmail()),"userId",u.getId(),"name",u.getName(),"email",u.getEmail());
    }
public Map<String,Object> me(){
        User u = currentUser.get();
        return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail());
}
    @Transactional
    public Map<String,Object> createTrip(Requests.CreateTrip r){
        if(r.endDate().isBefore(r.startDate()))
            throw new IllegalArgumentException("End date must be on or after start date");
        User u = currentUser.get();
        Trip t = tripRepository.save(new Trip(r.title(),r.destination(),r.startDate(),r.endDate(),r.budget(),r.currency().toUpperCase(),u));
        tripMemberRepository.save(new TripMember(t,u,Domain.Role.OWNER));
        return tripMemberRepository(tripAccess.get(id));
    }
    @Transactional(readOnly = true)
    public Map<String,Object> getTrip(UUID id){
        tripAccess.member(id,currentUser.get().getId());
        return tripMap(tripAccess.get(id));
    }
    @Transactional
    public Map<String,Object> upadateTrip(UUID id, Requests.CreateTrip r){
        tripAccess.admin(id,currentUser.get().getId());
        if(r.endDate().isBefore(r.startDate()))
            throw new IllegalArgumentException("Invalid dates");
        Trip t = tripAccess.get(id);
        t.update(r.title(),r.destination(),r.startDate(),r.endDate(),r.budget(),r.currency().toUpperCase());
        return tripMap(tripAccess.get(id));
    }

    @Transactional
    public void deleteTrip(UUID id){
        tripAccess.admin(id,currentUser.get().getId());
        tripRepository.delete(tripAccess.get(id));
    }

    private Map<String, Object> tripMap(Trip t) {

        return Map.of(
                "id", t.getId(),
                "title", t.getTitle(),
                "destination", t.getDestination(),
                "startDate", t.getStartDate(),
                "endDate", t.getEndDate(),
                "budget", t.getBudget(),
                "currency", t.getCurrency(),
                "ownerId", t.getOwner().getId()
        );
    }


}
