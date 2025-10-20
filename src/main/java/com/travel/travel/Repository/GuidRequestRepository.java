package com.travel.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.travel.Models.GuidRequest;

public interface GuidRequestRepository extends JpaRepository<GuidRequest, Long> {
    List<GuidRequest> findByTripId(Long tripId);
    List<GuidRequest> findByUserId(Long userId);
    List<GuidRequest> findByGuidId(Long guidId);
    List<GuidRequest> findByGuideBookingId(Long guideBookingId);
    List<GuidRequest> findByMultiRequestId(String multiRequestId);
}