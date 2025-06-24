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

        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setProfilePictures(user.getProfilePictures());
        existing.setRole(user.getRole());
        existing.setIsDeleted(user.getIsDeleted());

    }
}
