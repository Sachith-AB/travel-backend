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

import com.travel.travel.Models.Guid;
import com.travel.travel.Service.GuidService;

@RestController
@RequestMapping("/api/guides")
public class GuidController {

    @Autowired
    private GuidService guidService;

    @PostMapping
    public ResponseEntity<?> createGuid(@RequestBody Guid guid) {
        try {
            Guid savedGuid = guidService.createGuid(guid);
            return ResponseEntity.ok(savedGuid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGuidById(@PathVariable Long id) {
        Guid guid = guidService.getGuidById(id);
        if (guid == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guid);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getGuidByUserId(@PathVariable Long userId) {
        Guid guid = guidService.getGuidByUserId(userId);
        if (guid == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guid);
    }

    @GetMapping
    public ResponseEntity<List<Guid>> getAllGuids(@org.springframework.web.bind.annotation.RequestParam(required = false) java.util.Map<String, String> allParams) {
        if (allParams == null || allParams.isEmpty()) {
            return ResponseEntity.ok(guidService.getAllGuids());
        }
        return ResponseEntity.ok(guidService.getAllGuidsWithFilters(allParams));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGuid(@PathVariable Long id, @RequestBody Guid guid) {
        try {
            Guid updatedGuid = guidService.updateGuid(id, guid);
            return ResponseEntity.ok(updatedGuid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuid(@PathVariable Long id) {
        try {
            guidService.deleteGuid(id);
            return ResponseEntity.ok("Guid deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
