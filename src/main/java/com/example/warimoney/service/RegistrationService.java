package com.example.warimoney.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.warimoney.domain.User;
import com.example.warimoney.repository.UserRepository;

@Service
public class RegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  
  public RegistrationService(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  public User register(String username, String rawPassword) {
    String hashed = passwordEncoder.encode(rawPassword);
    // デフォルトロールは USER にする
    User user = new User(0, username, hashed, "USER");
    return userRepository.save(user);
  }
}
