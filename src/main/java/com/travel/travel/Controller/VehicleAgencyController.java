package com.travel.travel.Controller;

import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Service.VehicleAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}
