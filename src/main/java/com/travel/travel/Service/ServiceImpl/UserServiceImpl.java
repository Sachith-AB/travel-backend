package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.User;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Service.UserService;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
    
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setAge(updatedUser.getAge());
            return userRepository.save(existingUser);
        } else {
                throw new RuntimeException("User not found with id " + id);
        }
    }

	@Override
	public String deleteUser(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            userRepository.delete(existingUser);
            return "User deleted id = " + id;
        } else {
                throw new RuntimeException("User not found with id " + id);
        }
	}
}
