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

    // ユーザー登録
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
    
    // ユーザーをIDで取得
    public User getUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
	}
    
    // ユーザーをIDで取得（関連エンティティも一緒に）
    public User getUserWithProjects(Long userId) {
        return userRepository.findByIdWithProjects(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
