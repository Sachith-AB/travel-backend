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

    @Override
    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) throws Exception {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new Exception("Vehicle not found with ID: " + id));

//        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
//        existingVehicle.setVehicleNo(updatedVehicle.getVehicleNo());
//        existingVehicle.setRegistrationNo(updatedVehicle.getRegistrationNo());
//        existingVehicle.setPricePerKilometer(updatedVehicle.getPricePerKilometer());
//        existingVehicle.setImages(updatedVehicle.getImages());
//        existingVehicle.setAmenities(updatedVehicle.getAmenities());
//        existingVehicle.setAvailability(updatedVehicle.isAvailability());
//        existingVehicle.setCapacity(updatedVehicle.getCapacity());
//        existingVehicle.setAgency(updatedVehicle.getAgency());

        return vehicleRepository.save(existingVehicle);
    }




}
