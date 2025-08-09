package com.travel.travel.Service;

import java.util.List;

import com.travel.travel.Models.Vehicle;

public interface VehicleService {
    Vehicle registerVehicle(Vehicle vehicle) throws Exception;

    Vehicle getVehicleById(Long id) ;

    Vehicle updateVehicle(Long id, Vehicle vehicle) throws Exception;

    List<Vehicle> getVehiclesByAgencyId(Long agencyId);

    void deleteVehicle(Long id) throws Exception;

    List<Vehicle> getAllVehicles();
}
