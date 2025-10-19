// src/main/java/com/travel/travel/Repository/HotelRepository.java
package com.travel.travel.Repository;

import com.travel.travel.Models.Hotel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByUserId(Long userId);
    Long countByIsVerified(Boolean isVerified);
    Long countByCreatedAtAfter(LocalDateTime date);
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Long countByProvince(String province);
    List<Hotel> findTop4ByIsVerifiedOrderByCreatedAtDesc(Boolean isVerified);
}