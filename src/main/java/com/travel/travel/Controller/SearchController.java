package com.travel.travel.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.RequestDto.GuidSearchDTO;
import com.travel.travel.RequestDto.HotelSearchDTO;
import com.travel.travel.RequestDto.VehicleSearchDTO;
import com.travel.travel.Service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/guides")
    public ResponseEntity<List<GuidSearchDTO>> getAvailableGuides() {
        List<GuidSearchDTO> guides = searchService.getAvailableGuides();
        return ResponseEntity.ok(guides);
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelSearchDTO>> getHotelsByCity(@RequestParam String city) {
        List<HotelSearchDTO> hotels = searchService.getHotelsByCity(city);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleSearchDTO>> getVehiclesByCapacity(@RequestParam Integer capacity) {
        List<VehicleSearchDTO> vehicles = searchService.getVehiclesByCapacity(capacity);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllSearchData(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer capacity) {

        Map<String, Object> result = new HashMap<>();

        // Always get available guides
        result.put("guides", searchService.getAvailableGuides());

        // Get hotels if city is provided
        if (city != null && !city.trim().isEmpty()) {
            result.put("hotels", searchService.getHotelsByCity(city));
        }

        // Get vehicles if capacity is provided
        if (capacity != null && capacity > 0) {
            result.put("vehicles", searchService.getVehiclesByCapacity(capacity));
        }

        return ResponseEntity.ok(result);
    }
}
