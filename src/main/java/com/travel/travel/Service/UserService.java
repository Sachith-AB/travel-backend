package com.travel.travel.Service;

import java.util.List;
import java.util.Optional;

import com.travel.travel.Models.User;

public interface UserService {
    User createUser(User user);

    List<User> getUsers();

    Optional<User> findUserById(Long id);

    User updateUser(Long id, User updatedUser);

    String deleteUser(Long id);

}   
