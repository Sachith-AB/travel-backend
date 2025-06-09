package com.travel.travel.Controller;

import com.travel.travel.Models.Hotels;
import com.travel.travel.Models.User;
import com.travel.travel.Service.HotelService;
import com.travel.travel.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin(origins = "*")

public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotels> createHotels(@RequestBody Hotels hotel) {
        Hotels savedHotel = hotelService.createHotel(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotels> getHotelById(@PathVariable Long id) {
        Optional<Hotels> hotel = hotelService.findById(id);
        return hotel.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
