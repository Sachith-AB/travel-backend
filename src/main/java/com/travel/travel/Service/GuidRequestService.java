package com.travel.travel.Service;

import java.util.List;

import com.travel.travel.Models.GuidRequest;

public interface GuidRequestService {
    GuidRequest createRequest(GuidRequest request) throws Exception;
    GuidRequest updateRequest(Long id, GuidRequest request) throws Exception;
    void deleteRequest(Long id) throws Exception;
    List<GuidRequest> getAllRequests();
    List<GuidRequest> getRequestsByTripId(Long tripId);
    List<GuidRequest> getRequestsByUserId(Long userId);
    List<GuidRequest> getRequestsByGuidId(Long guidId);
    GuidRequest getById(Long id);
}