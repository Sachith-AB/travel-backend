package com.travel.travel.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.RequestDto.GuideResponseDTO;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/sample-guide")
    public ResponseEntity<GuideResponseDTO> getSampleGuide() {
        GuideResponseDTO sampleGuide = new GuideResponseDTO();
        sampleGuide.setId(1L);
        sampleGuide.setName("Sameemadhu Guide");
        sampleGuide.setProfilePicture("https://via.placeholder.com/150");
        sampleGuide.setBio("Passionate city guide specializing in Colombo's culture, history, and street food.");
        sampleGuide.setLanguagesSpoken(Arrays.asList("English", "Sinhala"));
        sampleGuide.setSpecialization(Arrays.asList("Wildlife Safari", "Beach & Water Sports"));
        sampleGuide.setExperienceYears(5);
        sampleGuide.setPrice(25.0);
        sampleGuide.setIsVerified(true);
        sampleGuide.setIsAvailable(true);
        sampleGuide.setStatus("Available");
        sampleGuide.setRating(4);
        sampleGuide.setReviewCount(12);
        
        return ResponseEntity.ok(sampleGuide);
    }

    @GetMapping("/sample-guides")
    public ResponseEntity<List<GuideResponseDTO>> getSampleGuides() {
        GuideResponseDTO guide1 = new GuideResponseDTO();
        guide1.setId(1L);
        guide1.setName("Sameemadhu Guide");
        guide1.setProfilePicture("https://via.placeholder.com/150");
        guide1.setBio("Passionate city guide specializing in Colombo's culture, history, and street food.");
        guide1.setLanguagesSpoken(Arrays.asList("English", "Sinhala"));
        guide1.setSpecialization(Arrays.asList("Wildlife Safari", "Beach & Water Sports"));
        guide1.setExperienceYears(5);
        guide1.setPrice(25.0);
        guide1.setIsVerified(true);
        guide1.setIsAvailable(true);
        guide1.setStatus("Available");
        guide1.setRating(4);
        guide1.setReviewCount(12);

        GuideResponseDTO guide2 = new GuideResponseDTO();
        guide2.setId(2L);
        guide2.setName("John Silva");
        guide2.setProfilePicture("https://via.placeholder.com/150");
        guide2.setBio("Expert wildlife guide with 10 years experience in national parks.");
        guide2.setLanguagesSpoken(Arrays.asList("English", "Tamil", "Sinhala"));
        guide2.setSpecialization(Arrays.asList("Wildlife Safari", "Adventure Tours"));
        guide2.setExperienceYears(10);
        guide2.setPrice(35.0);
        guide2.setIsVerified(true);
        guide2.setIsAvailable(false);
        guide2.setStatus("Busy");
        guide2.setRating(5);
        guide2.setReviewCount(25);

        return ResponseEntity.ok(Arrays.asList(guide1, guide2));
    }
}