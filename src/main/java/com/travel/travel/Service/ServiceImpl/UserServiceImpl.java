package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.User;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(User user) throws Exception {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) throws Exception {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, Long id) throws Exception {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Update basic info
        if (user.getFirstName() != null) {
            existing.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existing.setLastName(user.getLastName());
        }
        
        // Update contact and location info
        if (user.getPhoneNumber() != null) {
            existing.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getAddress() != null) {
            existing.setAddress(user.getAddress());
        }
        if (user.getCity() != null) {
            existing.setCity(user.getCity());
        }
        if (user.getCountry() != null) {
            existing.setCountry(user.getCountry());
        }
        
        // Update profile picture
        if (user.getProfilePicture() != null) {
            existing.setProfilePicture(user.getProfilePicture());
        }
        
        // Update other fields
        if (user.getProfilePictures() != null) {
            existing.setProfilePictures(user.getProfilePictures());
        }
        if (user.getRole() != null) {
            existing.setRole(user.getRole());
        }
        if (user.getIsDeleted() != null) {
            existing.setIsDeleted(user.getIsDeleted());
        }
    }

    @Override
    public Optional<User> findByPublicId(String docId) throws Exception {
        return userRepository.findByDocId(docId);
    }
}
