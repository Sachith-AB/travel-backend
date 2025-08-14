package com.travel.travel.Repository;

import com.travel.travel.Models.Vehicle;
import com.travel.travel.RequestDto.VehicleSearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByAgencyId(Long agencyId);

    @Query("""
            SELECT new com.travel.travel.RequestDto.VehicleSearchDTO(
                v.id,
                v.vehicleType,
                v.vehicleModel,
                v.vehicleNo,
                v.capacity,
                v.basePrice,
                v.pricePerKilometer
            )
            FROM Vehicle v
            WHERE v.capacity >= :capacity
            ORDER BY v.capacity ASC, v.id ASC
            """)
    List<VehicleSearchDTO> findVehiclesByMinCapacity(@Param("capacity") Integer capacity);
}
