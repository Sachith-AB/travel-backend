package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Driver;
import com.travel.travel.Repository.DriverRepository;
import com.travel.travel.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Override
    public Driver registerDriver(Driver driver) throws Exception {
        return driverRepository.save(driver);
    }
}
