package com.example.warimoney.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.warimoney.domain.User;
import com.example.warimoney.exception.UserAlreadyExistsException;
import com.example.warimoney.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(String userName, String password) {

        if (userRepository.existsByUsername(userName)) {
            throw new UserAlreadyExistsException(userName);
        }

        User user = User.builder()
                .username(userName)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .build();

        userRepository.save(user);
    }
}
