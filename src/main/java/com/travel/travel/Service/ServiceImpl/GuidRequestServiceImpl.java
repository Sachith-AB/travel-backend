package com.travel.travel.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.GuidRequest;
import com.travel.travel.Repository.GuidRequestRepository;
import com.travel.travel.Service.GuidRequestService;

@Service
public class GuidRequestServiceImpl implements GuidRequestService {

    @Autowired
    private GuidRequestRepository guidRequestRepository;

    @Override
    public GuidRequest createRequest(GuidRequest request) throws Exception {
        request.setStatus("pending");
        return guidRequestRepository.save(request);
    }

    @Override
    public GuidRequest updateRequest(Long id, GuidRequest request) throws Exception {
        GuidRequest existing = guidRequestRepository.findById(id)
                .orElseThrow(() -> new Exception("Request not found"));
        existing.setStatus(request.getStatus());
        // Optionally update other fields if needed
        return guidRequestRepository.save(existing);
    }

    @Override
    public void deleteRequest(Long id) throws Exception {
        if (!guidRequestRepository.existsById(id)) {
            throw new Exception("Request not found");
        }
        guidRequestRepository.deleteById(id);
    }

    @Override
    public List<GuidRequest> getAllRequests() {
        return guidRequestRepository.findAll();
    }

    @Override
    public List<GuidRequest> getRequestsByTripId(Long tripId) {
        return guidRequestRepository.findByTripId(tripId);
    }

    @Override
    public List<GuidRequest> getRequestsByUserId(Long userId) {
        return guidRequestRepository.findByUserId(userId);
    }

    @Override
    public List<GuidRequest> getRequestsByGuidId(Long guidId) {
        return guidRequestRepository.findByGuidId(guidId);
    }

    @Override
    public GuidRequest getById(Long id) {
        return guidRequestRepository.findById(id).orElse(null);
    }
}