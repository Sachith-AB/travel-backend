package com.travel.travel.Service;

import java.util.List;

import com.travel.travel.Models.Guid;

public interface GuidService {
    Guid createGuid(Guid guid) throws Exception;
    Guid getGuidById(Long id);
    Guid getGuidByUserId(Long userId);
    List<Guid> getAllGuids();
    Guid updateGuid(Long id, Guid guid) throws Exception;
    void deleteGuid(Long id) throws Exception;
}
