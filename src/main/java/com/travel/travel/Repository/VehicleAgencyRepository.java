package com.travel.travel.Repository;

import com.travel.travel.Models.VehicleAgency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VehicleAgencyRepository extends JpaRepository<VehicleAgency, Long> {
    VehicleAgency findByUserId(Long userId);
    Long countByIsVerified(Boolean isVerified);
    Long countByCreatedAtAfter(LocalDateTime date);
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
