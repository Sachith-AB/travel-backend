package com.travel.travel.Repository;

import com.travel.travel.Models.VehicleAgency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleAgencyRepository extends JpaRepository<VehicleAgency, Long> {
    VehicleAgency findByUserId(Long userId);
}
