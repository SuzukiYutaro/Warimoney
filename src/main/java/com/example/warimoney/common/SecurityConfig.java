package com.example.warimoney.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // パスワードの暗号化
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        // ログインページの許可設定
        .formLogin(login -> login // フォーム認証を使う
        		.loginPage("/toLogin") // ログインページの設定
            .defaultSuccessUrl("/projects") // 認証成功時のデフォルトの遷移先
            .permitAll())

        // リクエストの許可設定
        .authorizeHttpRequests(authz -> authz
            //参照権限
            .requestMatchers("/")
            .permitAll()
            .requestMatchers("/toLogin")
            .permitAll()
            .requestMatchers("/projects")
            .hasRole("USER"));

    return http.build();
  }

}
