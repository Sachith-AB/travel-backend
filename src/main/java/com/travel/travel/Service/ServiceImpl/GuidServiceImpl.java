package com.travel.travel.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Guid;
import com.travel.travel.Repository.GuidRepository;
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
    public Guid getGuidByUserId(Long userId) {
        return guidRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public List<Guid> getAllGuids() {
        return guidRepository.findAll();
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
