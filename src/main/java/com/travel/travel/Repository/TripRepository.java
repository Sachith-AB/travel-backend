package com.travel.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.travel.Models.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(Long userId);
    
    List<Trip> findByUserIdOrderByCreatedAtDesc(Long userId);
}
