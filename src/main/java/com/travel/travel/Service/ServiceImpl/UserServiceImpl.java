package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.User;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
