package com.travel.travel.Service;

import com.travel.travel.Models.User;

import java.util.Optional;

public interface UserService {
    void createUser(User user) throws Exception;

    Optional<User> getUserById(Long id) throws Exception;

    void updateUser(User user, Long id) throws Exception;

    Optional<User> findByPublicId(String docId) throws Exception;
}
