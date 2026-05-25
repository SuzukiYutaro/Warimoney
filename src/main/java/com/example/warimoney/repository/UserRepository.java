package com.example.warimoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.warimoney.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ユーザ名で検索（ログイン時に使用）
    Optional<User> findByUsername(String username);

    // 指定されたユーザ名がすでに存在するか（ユーザ登録時に使用）
    boolean existsByUsername(String username);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects WHERE u.id = :id")
    Optional<User> findByIdWithProjects(Long id);
}
