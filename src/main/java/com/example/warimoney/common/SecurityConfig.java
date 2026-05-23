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
//				// CSRF トークンを設定していないため、CSRF をオフにする
//				.csrf(csrf -> csrf.disable())

				// ページの認可の設定
				.authorizeHttpRequests((authorize) -> authorize
						// 認証不要パスの設定
						.requestMatchers("/", "/register", "/login").permitAll()
						// USER ロールを持ったユーザのみアクセス許可
						.requestMatchers("/projects").hasRole("USER")
						// ADMIN ロールを持ったユーザのみアクセス許可
						.requestMatchers("/admin").hasRole("ADMIN")
						// 上記で設定したパス以外は認証済みでなければアクセス拒否
						.anyRequest().authenticated())
				.formLogin(login -> login
						.loginPage("/login")
						// ログインページのリクエスト先
						.loginProcessingUrl("/login")
						// ログイン成功
						.defaultSuccessUrl("/projects")
						// ログイン失敗時のリクエスト先
						.failureUrl("/login?error=true"));
		// @formatter:on
		return http.build();
	}

	// ハッシュ化のためにクラスを Bean 化
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
