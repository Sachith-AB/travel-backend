package com.travel.travel.Repository;

import com.travel.travel.Models.GuidRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuidRequestRepository extends JpaRepository<GuidRequest, Long> {
    List<GuidRequest> findByTripId(Long tripId);
    List<GuidRequest> findByUserId(Long userId);
    List<GuidRequest> findByGuidId(Long guidId);
    Long countByGuidId(Long guidId);
    List<GuidRequest> findTop5ByOrderByCreatedAtDesc();
}