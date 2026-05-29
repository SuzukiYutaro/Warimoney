package com.example.warimoney.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 必須のアノテーション
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				//				CSRF をオフにする
				//				.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(authz -> authz
						// 認証不要
						.requestMatchers("/", "/register", "/login", "/css/**", "/error/**","/js/**").permitAll()

						// プロジェクト関連は USER ロール必須
						.requestMatchers("/projects/**").hasRole("USER")

						// 管理者ページ
						.requestMatchers("/admin/**").hasRole("ADMIN")

						// その他は認証必須
						.anyRequest().authenticated())

				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/projects")
						.failureUrl("/login?error=true")
						.permitAll())

				.logout(logout -> logout
						.logoutSuccessUrl("/")
						.permitAll());
		// @formatter:on
		return http.build();
	}

	// ハッシュ化
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
