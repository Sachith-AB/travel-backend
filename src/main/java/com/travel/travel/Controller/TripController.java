package com.travel.travel.Controller;



import com.travel.travel.Models.Trip;
import com.travel.travel.Service.TripService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    TripService tripService;

    @PostMapping("")
    ResponseEntity<?> createTrip(@RequestBody Trip trip) throws Exception {
        try{
            tripService.createTrip(trip);
            return ResponseEntity.ok("OK");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
            System.out.println("Requested Trip ID: " + id); // Add this line
        try{
            Optional<Trip> trip = tripService.tripGetById(id);
            return ResponseEntity.ok(trip);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
