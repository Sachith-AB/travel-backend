package com.travel.travel.Controller;

import com.travel.travel.Models.Vehicle;
import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<?> registerVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle savedVehicle = vehicleService.registerVehicle(vehicle);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        if (vehicle == null) {
            return ResponseEntity.status(404).body("Vehicle not found with ID: " + id);
        }
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        try {
            Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicle);
            return ResponseEntity.ok(updatedVehicle);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<?> getVehiclesByAgencyId(@PathVariable Long agencyId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByAgencyId(agencyId);
        if (vehicles.isEmpty()) {
            return ResponseEntity.status(404).body("No vehicles found for agency ID: " + agencyId);
        }
        return ResponseEntity.ok(vehicles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok("Vehicle deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }





}

