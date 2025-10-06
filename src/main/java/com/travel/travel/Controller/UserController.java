package com.travel.travel.Controller;

import com.travel.travel.Models.User;
import com.travel.travel.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) throws Exception{
        try {
            Optional<User> user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) throws Exception{
        try {
            userService.updateUser(user, id);
            return ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/public/{docId}")
    public ResponseEntity<Optional<User>> getUserByPublicId(@PathVariable String docId) {
        try {
            Optional<User> user = userService.findByPublicId(docId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/fix-user-data")
    public ResponseEntity<?> fixUserData() {
        try {
            // Update the existing user to have proper name data
            Optional<User> existingUser = userService.getUserById(1L);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setFirstName("Sameemadhu");
                user.setLastName("Guide");
                userService.updateUser(user, 1L);
                return ResponseEntity.ok("User data updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
