package com.travel.travel.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.Models.User;
import com.travel.travel.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    ResponseEntity<?> createUser(@RequestBody User user) throws Exception{
        try {
            userService.createUser(user);
            return ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception{
        try {
            Optional<User> user = userService.getUserById(id);
            return user.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) throws Exception{
        try {
            userService.updateUser(user, id);
            // Fetch and return the updated user
            Optional<User> updatedUser = userService.getUserById(id);
            return updatedUser.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/public/{docId}")
    public ResponseEntity<User> getUserByPublicId(@PathVariable String docId) {
        try {
            Optional<User> user = userService.findByPublicId(docId);
            return user.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
