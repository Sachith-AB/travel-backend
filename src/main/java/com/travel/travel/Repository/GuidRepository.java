package com.travel.travel.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.travel.Models.Guid;

public interface GuidRepository extends JpaRepository<Guid, Long> {
    Optional<Guid> findByUserId(Long userId);
    Long countByIsVerified(Boolean isVerified);
    Long countByCreatedAtAfter(LocalDateTime date);
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Guid> findTop2ByIsVerifiedOrderByCreatedAtDesc(Boolean isVerified);
}
