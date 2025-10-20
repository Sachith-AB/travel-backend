// src/main/java/com/travel/travel/Repository/HotelRepository.java
package com.travel.travel.Repository;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.travel.Models.Hotel;
import com.travel.travel.RequestDto.HotelSearchDTO;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("""
            SELECT new com.travel.travel.RequestDto.HotelSearchDTO(
                h.id,
                h.hotelName,
                h.city,
                h.description,
                h.type
            )
            FROM Hotel h
            WHERE LOWER(h.city) LIKE LOWER(CONCAT('%', :city, '%'))
            """)
    List<HotelSearchDTO> findHotelsByCity(@Param("city") String city);
    Optional<Hotel> findByUserId(Long userId);
}