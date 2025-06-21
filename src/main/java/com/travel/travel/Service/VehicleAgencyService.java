package com.travel.travel.Service;

import com.travel.travel.Models.VehicleAgency;

import java.util.List;
import java.util.Optional;

public interface VehicleAgencyService {
    void registerVehicleAgency(VehicleAgency vehicleAgency) throws Exception;

    List<VehicleAgency> getVehicleAgencies() throws  Exception;

    VehicleAgency getAgenciesByUserId(Long userId) throws Exception;
    Optional<VehicleAgency> getAgencyById(Long id) throws Exception;
}
