package com.travel.travel.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.Models.Guid;
import com.travel.travel.Models.User;
import com.travel.travel.Service.GuidService;
import com.travel.travel.Service.UserService;

@RestController
@RequestMapping("/api/guides")
public class GuidController {

    @Autowired
    private GuidService guidService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createGuid(@RequestBody java.util.Map<String, Object> payload) {
        try {
            // Extract userId from payload
            Long userId = null;
            if (payload.containsKey("userId")) {
                Object userIdObj = payload.get("userId");
                if (userIdObj instanceof Number) {
                    userId = ((Number) userIdObj).longValue();
                }
            }
            
            // If userId is not set, we cannot proceed
            if (userId == null) {
                return ResponseEntity.badRequest().body("Error: User ID is required");
            }
            
            // Fetch the user
            Optional<User> userOpt = userService.getUserById(userId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: User not found");
            }
            
            User user = userOpt.get();
            
            // Create Guid object from payload
            Guid guid = new Guid();
            guid.setUser(user);
            
            if (payload.containsKey("bio")) guid.setBio((String) payload.get("bio"));
            if (payload.containsKey("languagesSpoken")) guid.setLanguagesSpoken((List<String>) payload.get("languagesSpoken"));
            if (payload.containsKey("specialization")) guid.setSpecialization((List<String>) payload.get("specialization"));
            if (payload.containsKey("experienceYears")) {
                Object exp = payload.get("experienceYears");
                guid.setExperienceYears(exp instanceof Number ? ((Number) exp).intValue() : null);
            }
            if (payload.containsKey("hoursRate")) {
                Object rate = payload.get("hoursRate");
                guid.setHoursRate(rate instanceof Number ? ((Number) rate).doubleValue() : null);
            }
            if (payload.containsKey("sltaLicenseId")) guid.setSltaLicenseId((String) payload.get("sltaLicenseId"));
            if (payload.containsKey("sltaLicensePhoto")) guid.setSltaLicensePhoto((List<String>) payload.get("sltaLicensePhoto"));
            if (payload.containsKey("sltaLicenseExpiry")) {
                Object expiry = payload.get("sltaLicenseExpiry");
                if (expiry != null) {
                    guid.setSltaLicenseExpiry(java.time.LocalDateTime.parse((String) expiry, java.time.format.DateTimeFormatter.ISO_DATE_TIME));
                }
            }
            if (payload.containsKey("nicNumber")) guid.setNicNumber((String) payload.get("nicNumber"));
            if (payload.containsKey("nicPhotoFront")) guid.setNicPhotoFront((List<String>) payload.get("nicPhotoFront"));
            if (payload.containsKey("nicPhotoBack")) guid.setNicPhotoBack((List<String>) payload.get("nicPhotoBack"));
            
            // Set default values
            guid.setIsVerified(false);
            guid.setIsAvailable(true);
            
            // Save the guide
            Guid savedGuid = guidService.createGuid(guid);
            
            // Update user role to GUIDE
            user.setRole("GUIDE");
            userService.updateUser(user, userId);
            
            return ResponseEntity.ok(savedGuid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGuidById(@PathVariable Long id) {
        Guid guid = guidService.getGuidById(id);
        if (guid == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guid);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getGuidByUserId(@PathVariable Long userId) {
        Guid guid = guidService.getGuidByUserId(userId);
        if (guid == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guid);
    }
    
    @GetMapping("/docId/{docId}")
    public ResponseEntity<?> getGuidByUserDocId(@PathVariable String docId) {
        try {
            Optional<User> userOpt = userService.findByPublicId(docId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }
            
            Guid guid = guidService.getGuidByUserId(userOpt.get().getId());
            if (guid == null) {
                return ResponseEntity.status(404).body("Guide not found for this user");
            }
            
            return ResponseEntity.ok(guid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Guid>> getAllGuids(@org.springframework.web.bind.annotation.RequestParam(required = false) java.util.Map<String, String> allParams) {
        if (allParams == null || allParams.isEmpty()) {
            return ResponseEntity.ok(guidService.getAllGuids());
        }
        return ResponseEntity.ok(guidService.getAllGuidsWithFilters(allParams));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGuid(@PathVariable Long id, @RequestBody Guid guid) {
        try {
            Guid updatedGuid = guidService.updateGuid(id, guid);
            return ResponseEntity.ok(updatedGuid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuid(@PathVariable Long id) {
        try {
            guidService.deleteGuid(id);
            return ResponseEntity.ok("Guid deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
