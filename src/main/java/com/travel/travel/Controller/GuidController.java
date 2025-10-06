package com.travel.travel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.Models.Guid;
import com.travel.travel.RequestDto.GuideAvailabilityResponse;
import com.travel.travel.RequestDto.GuideResponseDTO;
import com.travel.travel.RequestDto.GuideSearchFilters;
import com.travel.travel.Service.GuidService;

@RestController
@RequestMapping("/api/guides")
public class GuidController {

    @Autowired
    private GuidService guidService;

    /**
     * Create a new guide
     */
    @PostMapping
    public ResponseEntity<?> createGuid(@RequestBody Guid guid) {
        try {
            Guid savedGuid = guidService.createGuid(guid);
            return ResponseEntity.ok(savedGuid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all available guides - matches frontend fetchGuides()
     */
    @GetMapping
    public ResponseEntity<List<GuideResponseDTO>> getAllGuids() {
        try {
            List<GuideResponseDTO> guides = guidService.getAllGuidesDTOs();
            return ResponseEntity.ok(guides);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Search guides with filters - matches frontend fetchGuidesWithFilters()
     */
    @GetMapping("/search")
    public ResponseEntity<List<GuideResponseDTO>> searchGuides(
            @RequestParam(required = false) List<String> languages,
            @RequestParam(required = false) List<String> specialties,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Boolean availableOnly) {
        try {
            GuideSearchFilters filters = new GuideSearchFilters();
            filters.setLanguages(languages);
            filters.setSpecialties(specialties);
            filters.setMinPrice(minPrice);
            filters.setMaxPrice(maxPrice);
            filters.setMinRating(minRating);
            filters.setAvailableOnly(availableOnly);

            List<GuideResponseDTO> guides = guidService.searchGuidesDTOs(filters);
            return ResponseEntity.ok(guides);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get guide by ID - matches frontend fetchGuideById()
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGuidById(@PathVariable Long id) {
        try {
            GuideResponseDTO guide = guidService.getGuideDTOById(id);
            if (guide == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(guide);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Check guide availability for specific dates - matches frontend checkGuideAvailability()
     */
    @GetMapping("/{id}/availability")
    public ResponseEntity<GuideAvailabilityResponse> checkGuideAvailability(
            @PathVariable Long id,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            GuideAvailabilityResponse response = guidService.checkGuideAvailability(id, startDate, endDate);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            GuideAvailabilityResponse errorResponse = new GuideAvailabilityResponse(
                false, 
                "Error checking availability: " + e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Get guide by user ID (existing functionality)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getGuidByUserId(@PathVariable Long userId) {
        try {
            Guid guid = guidService.getGuidByUserId(userId);
            if (guid == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(guid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Update guide
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGuid(@PathVariable Long id, @RequestBody Guid guid) {
        try {
            Guid updatedGuid = guidService.updateGuid(id, guid);
            return ResponseEntity.ok(updatedGuid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Delete guide
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuid(@PathVariable Long id) {
        try {
            guidService.deleteGuid(id);
            return ResponseEntity.ok("Guide deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Create sample guide data for testing
     */
    @PostMapping("/create-sample")
    public ResponseEntity<?> createSampleGuide() {
        try {
            // This is just for testing - you can remove this later
            return ResponseEntity.ok("Sample guide creation endpoint - implement as needed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Fix existing guide data for testing
     */
    @PostMapping("/fix-guide-data")
    public ResponseEntity<?> fixGuideData() {
        try {
            Guid existingGuide = guidService.getGuidById(1L);
            if (existingGuide != null) {
                existingGuide.setIsVerified(true);
                existingGuide.setIsAvailable(true);
                guidService.updateGuid(1L, existingGuide);
                return ResponseEntity.ok("Guide data updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
