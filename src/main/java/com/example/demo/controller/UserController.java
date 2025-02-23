package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;




import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

   
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    
    
//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@Validated @RequestBody User user, BindingResult result) {
//        // Check for validation errors
//        if (result.hasErrors()) {
//            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
//            return ResponseEntity.badRequest().body(errorMessage);
//        }
//
//      
//        if (user.getEmail() == null || !user.getEmail().endsWith("@gmail.com")) {
//            return ResponseEntity.badRequest().body("Only Gmail email addresses are allowed");
//        }
//
//       
//        Optional<User> existingUserByEmail = userService.findByEmail(user.getEmail());
//        if (existingUserByEmail.isPresent()) {
//            return ResponseEntity.badRequest().body("Email already exists");
//        }
//
//        // Check if the mobile number already exists
//        Optional<User> existingUserByMobile = userService.findByMobile(user.getMobile());
//        if (existingUserByMobile.isPresent()) {
//            return ResponseEntity.badRequest().body("Mobile number already exists");
//        }
//
//        // Set default role and save the user
//        user.setRole("USER");
//        userService.saveUser(user);
//        return ResponseEntity.ok("Signup successful");
//    }
//
// 
    

    
    
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        if (!user.getEmail().endsWith("@gmail.com")) {
            response.put("message", "Only Gmail email addresses are allowed");
            return ResponseEntity.badRequest().body(response);
        }

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        if (userService.findByMobile(user.getMobile()).isPresent()) {
            response.put("message", "Mobile number already exists");
            return ResponseEntity.badRequest().body(response);
        }

        user.setRole("USER");
        userService.saveUser(user);

        response.put("message", "Signup successful");
        return ResponseEntity.ok(response);
    }

    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            // If user credentials are valid, return user details including balance
            User loggedInUser = existingUser.get();
            
            // Return the full user data including balance
            Map<String, Object> response = new HashMap<>();
            response.put("id", loggedInUser.getId());
            response.put("name", loggedInUser.getName());
            response.put("role", loggedInUser.getRole());

            
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @PostMapping("/forget-password")
    public String forgetPassword(@RequestParam String email) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "User with this email does not exist.";
        }

        User user = userOptional.get();
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userService.save(user);

        // Simulate sending email (In real application, use an email service)
//        return "A reset link has been sent to your email: " + email + ". Reset token: " + resetToken;
        
        return "A reset link has been sent to your email: " + email + ". Get Reset Token From Databases: ";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<User> userOptional = userService.findByResetToken(token);

        if (userOptional.isEmpty()) {
            return "Invalid reset token.";
        }

        User user = userOptional.get();
        user.setPassword(newPassword); // In real application, hash the password
        user.setResetToken(null);
        userService.save(user);

        return "Password reset successful.";
    }

  

    



    
}

