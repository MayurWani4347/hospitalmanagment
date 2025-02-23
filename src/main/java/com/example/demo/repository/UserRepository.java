package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByMobile(String mobile);
    Optional<User> findById(Long id);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByResetToken(String resetToken);

}

