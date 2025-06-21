package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Repository.VehicleAgencyRepository;
import com.travel.travel.Service.VehicleAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleAgencyImpl implements VehicleAgencyService {

    @Autowired
    VehicleAgencyRepository vehicleAgencyRepository;

    @Override
    public VehicleAgency registerVehicleAgency(VehicleAgency vehicleAgency) throws Exception {
        return  vehicleAgencyRepository.save(vehicleAgency);
    }
}
