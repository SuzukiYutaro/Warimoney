package com.example.warimoney.repository;

import com.example.warimoney.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ユーザ名で検索（ログイン時に使用）
    Optional<User> findByUsername(String username);

    // 指定されたユーザ名がすでに存在するか（ユーザ登録時に使用）
    boolean existsByUsername(String username);
}
