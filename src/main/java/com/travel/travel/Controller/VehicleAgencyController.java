package com.travel.travel.Controller;

import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Service.VehicleAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/vehicleAgencies")
public class VehicleAgencyController {

    @Autowired
    VehicleAgencyService vehicleAgencyService;

    @PostMapping("/register")
    public ResponseEntity<?> registerHotel(@RequestBody VehicleAgency vehicleAgency){
        try {
            vehicleAgencyService.registerVehicleAgency(vehicleAgency);
            return ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public  ResponseEntity<List<VehicleAgency>> getVehicleAgencies(){
        try {
            List<VehicleAgency> agencies = vehicleAgencyService.getVehicleAgencies();
            return ResponseEntity.ok(agencies);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public  ResponseEntity<VehicleAgency> getVehicleAgencyByUserId(@PathVariable Long userId){
        try {
           VehicleAgency agency = vehicleAgencyService.getAgenciesByUserId(userId);
            return ResponseEntity.ok(agency);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Optional<VehicleAgency>> getVehicleAgencyById(@PathVariable Long id){
        try {
            Optional<VehicleAgency> agency = vehicleAgencyService.getAgencyById(id);
            return ResponseEntity.ok(agency);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAgency(@PathVariable Long id, @RequestBody VehicleAgency agency) throws Exception {
        vehicleAgencyService.updateAgency(id, agency);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        vehicleAgencyService.deleteAgency(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
