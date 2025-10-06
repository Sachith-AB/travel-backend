package com.travel.travel.Service.ServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Guid;
import com.travel.travel.Repository.GuidRepository;
import com.travel.travel.RequestDto.GuideAvailabilityResponse;
import com.travel.travel.RequestDto.GuideResponseDTO;
import com.travel.travel.RequestDto.GuideSearchFilters;
import com.travel.travel.Service.GuidService;

@Service
public class GuidServiceImpl implements GuidService {

    @Autowired
    private GuidRepository guidRepository;

    @Override
    public Guid createGuid(Guid guid) throws Exception {
        return guidRepository.save(guid);
    }

    @Override
    public Guid getGuidById(Long id) {
        return guidRepository.findById(id).orElse(null);
    }

    @Override
    public GuideResponseDTO getGuideDTOById(Long id) {
        Guid guide = guidRepository.findById(id).orElse(null);
        return guide != null ? new GuideResponseDTO(guide) : null;
    }

    @Override
    public Guid getGuidByUserId(Long userId) {
        return guidRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public List<Guid> getAllGuids() {
        return guidRepository.findAll();
    }

    @Override
    public List<GuideResponseDTO> getAllGuidesDTOs() {
        return guidRepository.findAll()
                .stream()
                .map(GuideResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<GuideResponseDTO> searchGuidesDTOs(GuideSearchFilters filters) {
        // If no filters provided, return all guides
        if (filters == null) {
            return getAllGuidesDTOs();
        }

        // Use the custom query method with filters
        List<Guid> guides = guidRepository.findGuidesWithFilters(
            filters.getLanguages(),
            filters.getSpecialties(),
            filters.getMinPrice(),
            filters.getMaxPrice(),
            filters.getAvailableOnly() != null ? filters.getAvailableOnly() : false
        );

        return guides.stream()
                .map(GuideResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public GuideAvailabilityResponse checkGuideAvailability(Long guideId, String startDate, String endDate) {
        try {
            // Find the guide
            Guid guide = guidRepository.findById(guideId).orElse(null);
            if (guide == null) {
                return new GuideAvailabilityResponse(false, "Guide not found");
            }

            // Check if guide is generally available
            if (!guide.getIsAvailable()) {
                return new GuideAvailabilityResponse(false, "Guide is not available");
            }

            // Parse dates
            LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

            // Validate date range
            if (start.isAfter(end)) {
                return new GuideAvailabilityResponse(false, "Start date cannot be after end date");
            }

            if (start.isBefore(LocalDate.now())) {
                return new GuideAvailabilityResponse(false, "Start date cannot be in the past");
            }

            // TODO: Here you would check against actual bookings/reservations
            // For now, we'll just check the basic availability flag
            // In a real implementation, you'd check against a bookings table

            return new GuideAvailabilityResponse(true, "Guide is available for the requested dates");

        } catch (Exception e) {
            return new GuideAvailabilityResponse(false, "Error checking availability: " + e.getMessage());
        }
    }

    @Override
    public Guid updateGuid(Long id, Guid guid) throws Exception {
        Guid existingGuid = guidRepository.findById(id).orElse(null);
        if (existingGuid == null) {
            throw new Exception("Guid not found");
        }
        // Update fields
        existingGuid.setBio(guid.getBio());
        existingGuid.setLanguagesSpoken(guid.getLanguagesSpoken());
        existingGuid.setSpecialization(guid.getSpecialization());
        existingGuid.setExperienceYears(guid.getExperienceYears());
        existingGuid.setHoursRate(guid.getHoursRate());
        existingGuid.setIsVerified(guid.getIsVerified());
        existingGuid.setIsAvailable(guid.getIsAvailable());
        existingGuid.setSltaLicenseId(guid.getSltaLicenseId());
        existingGuid.setSltaLicensePhoto(guid.getSltaLicensePhoto());
        existingGuid.setSltaLicenseExpiry(guid.getSltaLicenseExpiry());
        existingGuid.setNicNumber(guid.getNicNumber());
        existingGuid.setNicPhotoFront(guid.getNicPhotoFront());
        existingGuid.setNicPhotoBack(guid.getNicPhotoBack());
        // Do not update createdAt or user

        return guidRepository.save(existingGuid);
    }

    @Override
    public void deleteGuid(Long id) throws Exception {
        Guid existingGuid = guidRepository.findById(id).orElse(null);
        if (existingGuid == null) {
            throw new Exception("Guid not found");
        }
        guidRepository.deleteById(id);
    }
}
