package com.travel.travel.Service;

import com.travel.travel.Models.Vehicle;

public interface VehicleService {
    Vehicle registerVehicle(Vehicle vehicle) throws Exception;

    Vehicle getVehicleById(Long id) ;
}
