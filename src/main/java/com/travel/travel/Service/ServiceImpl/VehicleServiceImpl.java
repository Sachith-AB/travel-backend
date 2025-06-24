package com.travel.travel.Service.ServiceImpl;


import com.travel.travel.Models.Vehicle;
import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Repository.VehicleRepository;
import com.travel.travel.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle registerVehicle(Vehicle vehicle) throws Exception {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElse(null);

    }


}
