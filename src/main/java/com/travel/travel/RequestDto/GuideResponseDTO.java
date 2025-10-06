package com.travel.travel.RequestDto;

import java.util.List;

import lombok.Data;

@Data
public class GuideResponseDTO {
    private Long id;
    private String name;  // Combined first name + last name
    private String profilePicture;  // First profile picture URL
    private String bio;
    private List<String> languagesSpoken;
    private List<String> specialization;
    private Integer experienceYears;
    private Double price;  // Mapped from hoursRate
    private Boolean isVerified;
    private Boolean isAvailable;
    private String status;  // Available/Busy status
    private Integer rating;  // Placeholder for future rating system
    private Integer reviewCount;  // Placeholder for future review system
    
    // Constructor to create DTO from Guid entity
    public GuideResponseDTO(com.travel.travel.Models.Guid guide) {
        this.id = guide.getId();
        
        // Combine user's first and last name
        if (guide.getUser() != null) {
            String firstName = guide.getUser().getFirstName() != null ? guide.getUser().getFirstName() : "";
            String lastName = guide.getUser().getLastName() != null ? guide.getUser().getLastName() : "";
            String fullName = (firstName + " " + lastName).trim();
            
            // If name is still empty, use email or default
            if (fullName.isEmpty()) {
                if (guide.getUser().getEmail() != null) {
                    // Extract name from email (before @)
                    String emailName = guide.getUser().getEmail().split("@")[0];
                    this.name = emailName.replace(".", " ").replace("_", " ");
                } else {
                    this.name = "Guide #" + guide.getId();
                }
            } else {
                this.name = fullName;
            }
            
            // Get first profile picture if available
            if (guide.getUser().getProfilePictures() != null && !guide.getUser().getProfilePictures().isEmpty()) {
                this.profilePicture = guide.getUser().getProfilePictures().get(0);
            } else {
                // Default profile picture URL or placeholder
                this.profilePicture = "/default-avatar.png";
            }
        } else {
            this.name = "Guide #" + guide.getId();
            this.profilePicture = "/default-avatar.png";
        }
        
        this.bio = guide.getBio();
        this.languagesSpoken = guide.getLanguagesSpoken();
        this.specialization = guide.getSpecialization();
        this.experienceYears = guide.getExperienceYears();
        this.price = guide.getHoursRate() != null ? guide.getHoursRate() : 0.0;  // Default to 0 if null
        
        // Handle null boolean values
        this.isVerified = guide.getIsVerified() != null ? guide.getIsVerified() : false;
        this.isAvailable = guide.getIsAvailable() != null ? guide.getIsAvailable() : false;
        
        // Set status based on availability
        this.status = (this.isAvailable) ? "Available" : "Busy";
        
        // Placeholder values for rating system (implement later)
        this.rating = 0;
        this.reviewCount = 0;
    }
    
    // Default constructor
    public GuideResponseDTO() {}
}