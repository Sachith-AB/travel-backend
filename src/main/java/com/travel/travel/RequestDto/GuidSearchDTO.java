package com.travel.travel.RequestDto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GuidSearchDTO {
    private Long id;
    private String name;
    private String description;
    private Double pricePerDay;
    private Integer experienceYears;
    private Boolean isVerified;
    private Boolean isAvailable;
    private String sltaLicenseId;
    private LocalDateTime sltaLicenseExpiry;
    private String nicNumber;
    private LocalDateTime createdAt;

    public GuidSearchDTO(Long id, String name, String description, Double pricePerDay,
                        Integer experienceYears, Boolean isVerified, Boolean isAvailable,
                        String sltaLicenseId, LocalDateTime sltaLicenseExpiry,
                        String nicNumber, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.experienceYears = experienceYears;
        this.isVerified = isVerified;
        this.isAvailable = isAvailable;
        this.sltaLicenseId = sltaLicenseId;
        this.sltaLicenseExpiry = sltaLicenseExpiry;
        this.nicNumber = nicNumber;
        this.createdAt = createdAt;
    }
}
