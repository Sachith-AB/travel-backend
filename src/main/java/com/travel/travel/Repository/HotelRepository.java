package com.travel.travel.Repository;

import com.travel.travel.Models.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface HotelRepository extends JpaRepository<Hotels, Long> {
}
