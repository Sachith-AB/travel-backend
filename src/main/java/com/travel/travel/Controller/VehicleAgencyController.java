package com.travel.travel.Controller;

import com.travel.travel.Models.User;
import com.travel.travel.Models.VehicleAgency;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Service.VehicleAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/vehicleAgencies")
public class VehicleAgencyController {

    @Autowired
    VehicleAgencyService vehicleAgencyService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerVehicleAgency(@RequestBody Map<String, Object> request){
        try {
            // Extract userDocId from request
            String userDocId = (String) request.get("userDocId");
            if (userDocId == null || userDocId.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }

            // Find user by Firebase UID
            Optional<User> userOpt = userRepository.findByDocId(userDocId);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            User user = userOpt.get();

            // Check if user already has an agency
            VehicleAgency existing = vehicleAgencyService.getAgenciesByUserId(user.getId());
            if (existing != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Vehicle agency already registered for this user");
            }

            // Create VehicleAgency entity
            VehicleAgency vehicleAgency = new VehicleAgency();
            vehicleAgency.setAgencyName((String) request.get("agencyName"));
            vehicleAgency.setStreet((String) request.get("street"));
            vehicleAgency.setCity((String) request.get("city"));
            vehicleAgency.setDistrict((String) request.get("district"));
            vehicleAgency.setProvince((String) request.get("province"));
            vehicleAgency.setRegistrationNo((String) request.get("registrationNo"));
            vehicleAgency.setDescription((String) request.get("description"));
            vehicleAgency.setLicensePhoto((List<String>) request.get("licensePhoto"));
            vehicleAgency.setImages((List<String>) request.get("images"));
            vehicleAgency.setUser(user);

            // Save
            vehicleAgencyService.registerVehicleAgency(vehicleAgency);
            return ResponseEntity.ok("Vehicle agency registered successfully");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Registration failed: " + e.getMessage());
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
