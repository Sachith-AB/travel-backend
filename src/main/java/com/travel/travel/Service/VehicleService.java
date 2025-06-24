package com.travel.travel.Service;

import com.travel.travel.Models.Vehicle;
import com.travel.travel.Models.VehicleAgency;

import java.util.Optional;

public interface VehicleService {
    Vehicle registerVehicle(Vehicle vehicle) throws Exception;

    Vehicle getVehicleById(Long id) ;
}
