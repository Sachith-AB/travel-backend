package com.travel.travel.Service;

import com.travel.travel.Models.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DriverService {

    Driver registerDriver(Driver driver) throws Exception;

    List<Driver> getAllDrivers();

    Driver getDriverById(Long id);

    Page<Driver> getAllDrivers(Pageable pageable);

    List<Driver> getDriversByAgencyId(Long agencyId);

    Driver getDriverByUserId(Long userId);

    Driver updateDriver(Long id, Driver updatedDriver) throws Exception;
}

