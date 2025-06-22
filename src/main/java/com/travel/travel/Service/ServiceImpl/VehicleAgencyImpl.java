package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Repository.VehicleAgencyRepository;
import com.travel.travel.Service.VehicleAgencyService;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public void updateAgency(Long id, VehicleAgency updatedAgency) throws Exception {
        VehicleAgency existing = vehicleAgencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agency not found with ID: " + id));

//            existing.setAgencyName(updatedAgency.getAgencyName());
//            existing.setStreet(updatedAgency.getStreet());
//            existing.setCity(updatedAgency.getCity());
//            existing.setDistrict(updatedAgency.getDistrict());
//            existing.setProvince(updatedAgency.getProvince());
//            existing.setDescription(updatedAgency.getDescription());
    }

    @Override
    public void deleteAgency(Long id) {
        vehicleAgencyRepository.deleteById(id);
    }


}
