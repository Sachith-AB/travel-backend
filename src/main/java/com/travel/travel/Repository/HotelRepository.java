// src/main/java/com/travel/travel/Repository/HotelRepository.java
package com.travel.travel.Repository;

import com.travel.travel.Models.Hotel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByUserId(Long userId);
}