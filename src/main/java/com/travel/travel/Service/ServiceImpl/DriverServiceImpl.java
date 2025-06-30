package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Driver;
import com.travel.travel.Repository.DriverRepository;
import com.travel.travel.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Override
    public Driver registerDriver(Driver driver) throws Exception {
        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Driver> getAllDrivers(Pageable pageable) {
        return driverRepository.findAll(pageable);
    }

    @Override
    public List<Driver> getDriversByAgencyId(Long agencyId) {
        return driverRepository.findByAgency_Id(agencyId);
    }

    @Override
    public Driver getDriverByUserId(Long userId) {
        return driverRepository.findByUser_Id(userId);
    }

    @Override
    public Driver updateDriver(Long id, Driver updatedDriver) throws Exception {
        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new Exception("Driver not found with ID: " + id));

//        existingDriver.setAge(updatedDriver.getAge());
//        existingDriver.setExperience(updatedDriver.getExperience());
//        existingDriver.setDriverLicenseNumber(updatedDriver.getDriverLicenseNumber());
//        existingDriver.setDriverLicensePhoto(updatedDriver.getDriverLicensePhoto());
//        existingDriver.setLicenseExpiryDate(updatedDriver.getLicenseExpiryDate());
//        existingDriver.setAgency(updatedDriver.getAgency());
//        existingDriver.setUser(updatedDriver.getUser());

        return driverRepository.save(existingDriver);
    }

    @Override
    public void deleteDriver(Long id) throws Exception {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new Exception("Driver not found with ID: " + id));
        driverRepository.delete(driver);
    }
}
