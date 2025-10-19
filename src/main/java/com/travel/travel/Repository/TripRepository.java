package com.travel.travel.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.travel.Models.Trip;
import com.travel.travel.Models.Enum.TripStatus;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(Long userId);
    
    List<Trip> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    Long countByTripStatus(TripStatus status);
    
    Long countByCreatedAtAfter(LocalDateTime date);
    
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    List<Trip> findTop5ByOrderByCreatedAtDesc();
}
