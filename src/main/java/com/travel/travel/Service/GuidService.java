package com.travel.travel.Service;

import java.util.List;

import com.travel.travel.Models.Guid;
import com.travel.travel.RequestDto.GuideAvailabilityResponse;
import com.travel.travel.RequestDto.GuideResponseDTO;
import com.travel.travel.RequestDto.GuideSearchFilters;

public interface GuidService {
    Guid createGuid(Guid guid) throws Exception;
    Guid getGuidById(Long id);
    GuideResponseDTO getGuideDTOById(Long id);
    Guid getGuidByUserId(Long userId);
    List<Guid> getAllGuids();
    List<GuideResponseDTO> getAllGuidesDTOs();
    List<GuideResponseDTO> searchGuidesDTOs(GuideSearchFilters filters);
    GuideAvailabilityResponse checkGuideAvailability(Long guideId, String startDate, String endDate);
    Guid updateGuid(Long id, Guid guid) throws Exception;
    void deleteGuid(Long id) throws Exception;
}
