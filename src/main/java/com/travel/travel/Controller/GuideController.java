package com.travel.travel.Controller;

import com.travel.travel.Models.Guide;
import com.travel.travel.Models.Hotel;
import com.travel.travel.Service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guide")
@CrossOrigin(origins = "*")
public class GuideController {
    @Autowired
    private GuideService guideService;

    @PostMapping
    public ResponseEntity<Guide> createGuide(@RequestBody Guide guide) {
        Guide savedGuide = guideService.createGuide(guide);
        return new ResponseEntity<>(savedGuide, HttpStatus.CREATED);
    }
}
