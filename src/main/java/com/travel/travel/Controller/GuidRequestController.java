package com.travel.travel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.Models.GuidRequest;
import com.travel.travel.Service.GuidRequestService;

@RestController
@RequestMapping("/api/guid-requests")
public class GuidRequestController {

    @Autowired
    private GuidRequestService guidRequestService;

    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody GuidRequest request) {
        try {
            GuidRequest saved = guidRequestService.createRequest(request);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Long id, @RequestBody GuidRequest request) {
        try {
            GuidRequest updated = guidRequestService.updateRequest(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) {
        try {
            guidRequestService.deleteRequest(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<GuidRequest>> getAllRequests() {
        return ResponseEntity.ok(guidRequestService.getAllRequests());
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<GuidRequest>> getByTripId(@PathVariable Long tripId) {
        return ResponseEntity.ok(guidRequestService.getRequestsByTripId(tripId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GuidRequest>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(guidRequestService.getRequestsByUserId(userId));
    }

    @GetMapping("/guid/{guidId}")
    public ResponseEntity<List<GuidRequest>> getByGuidId(@PathVariable Long guidId) {
        return ResponseEntity.ok(guidRequestService.getRequestsByGuidId(guidId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuidRequest> getById(@PathVariable Long id) {
        GuidRequest req = guidRequestService.getById(id);
        if (req == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(req);
    }
}