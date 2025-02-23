package com.example.demo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public void saveUser(User user) {
        userRepository.save(user);
    }
    
   
  public List<User> getAllUsers() {
      return userRepository.findAll();  
  }
  
  public User findById(Long id) {
      Optional<User> user = userRepository.findById(id);
      return user.orElse(null);
  }

 
  
  public Optional<User> findByMobile(String mobile) {
      return userRepository.findByMobile(mobile);
  }
  
  public Optional<User> findByEmail(String email) {
  return userRepository.findByEmail(email);
}
  
  public Optional<User> findByResetToken(String resetToken) {
      return userRepository.findByResetToken(resetToken);
  }
  
  
  
  public void save(User user) {
      userRepository.save(user);
  }
  
}
