package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Repository.VehicleAgencyRepository;
import com.travel.travel.Service.VehicleAgencyService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleAgencyImpl implements VehicleAgencyService {

    @Autowired
    VehicleAgencyRepository vehicleAgencyRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void registerVehicleAgency(VehicleAgency vehicleAgency) throws Exception {
        // Check if agency already exists for this user
        if (vehicleAgency.getUser() != null && vehicleAgency.getUser().getId() != null) {
            VehicleAgency existing = vehicleAgencyRepository.findByUserId(vehicleAgency.getUser().getId());
            if (existing != null) {
                throw new RuntimeException("Vehicle agency already registered for this user");
            }
        }
        
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

                existing.setAgencyName(updatedAgency.getAgencyName());
                existing.setStreet(updatedAgency.getStreet());
                existing.setCity(updatedAgency.getCity());
                existing.setDistrict(updatedAgency.getDistrict());
                existing.setProvince(updatedAgency.getProvince());
                existing.setDescription(updatedAgency.getDescription());
    }

    @Override
    public void deleteAgency(Long id) {
        vehicleAgencyRepository.deleteById(id);
    }


}
