package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Repository.VehicleAgencyRepository;
import com.travel.travel.Service.VehicleAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleAgencyImpl implements VehicleAgencyService {

    @Autowired
    VehicleAgencyRepository vehicleAgencyRepository;

    @Override
    public void registerVehicleAgency(VehicleAgency vehicleAgency) throws Exception {
        vehicleAgencyRepository.save(vehicleAgency);
    }

    @Override
    public List<VehicleAgency> getVehicleAgencies() throws Exception {
        return vehicleAgencyRepository.findAll();
    }

    @Override
    public VehicleAgency getAgenciesByUserId(Long userId) throws Exception {
        return vehicleAgencyRepository.findByUserId(userId);
    }

    @Override
    public Optional<VehicleAgency> getAgencyById(Long id) throws Exception {
        return vehicleAgencyRepository.findById(id);
    }


}
