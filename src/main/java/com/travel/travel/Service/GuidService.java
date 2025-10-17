package com.travel.travel.Service;

import com.travel.travel.Models.Guid;
import java.util.List;

public interface GuidService {
    Guid createGuid(Guid guid) throws Exception;
    Guid getGuidById(Long id);
    Guid getGuidByUserId(Long userId);
    List<Guid> getAllGuids();
    List<Guid> getAllGuidsWithFilters(java.util.Map<String, String> filters);
    Guid updateGuid(Long id, Guid guid) throws Exception;
    void deleteGuid(Long id) throws Exception;
}
