package com.travel.travel.Controller;

import com.travel.travel.Models.Driver;
import com.travel.travel.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDriver(@RequestBody Driver driver) {
        try {
            Driver saved = driverService.registerDriver(driver);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        if (drivers.isEmpty()) {
            return ResponseEntity.status(404).body("No drivers found.");
        }
        return ResponseEntity.ok(drivers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable Long id) {
        Driver driver = driverService.getDriverById(id);
        if (driver == null) {
            return ResponseEntity.status(404).body("Driver not found with ID: " + id);
        }
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllDriversPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> drivers = driverService.getAllDrivers(pageable);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<?> getDriversByAgency(@PathVariable Long agencyId) {
        List<Driver> drivers = driverService.getDriversByAgencyId(agencyId);
        if (drivers.isEmpty()) {
            return ResponseEntity.status(404).body("No drivers found for agency ID: " + agencyId);
        }
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getDriverByUserId(@PathVariable Long userId) {
        Driver driver = driverService.getDriverByUserId(userId);
        if (driver == null) {
            return ResponseEntity.status(404).body("Driver not found for User ID: " + userId);
        }
        return ResponseEntity.ok(driver);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable Long id, @RequestBody Driver updatedDriver) {
        try {
            Driver driver = driverService.updateDriver(id, updatedDriver);
            return ResponseEntity.ok(driver);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteDriver(@PathVariable Long id) {
//        try {
//            driverService.deleteDriver(id);
//            return ResponseEntity.ok("Driver deleted successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(404).body("Error: " + e.getMessage());
//        }
//    }
}
