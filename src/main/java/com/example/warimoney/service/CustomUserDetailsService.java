package com.example.warimoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.warimoney.common.LoginUserDetails;
import com.example.warimoney.domain.User;
import com.example.warimoney.repository.LoginRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private LoginRepository repository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = repository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found for username : " + username));
      return new LoginUserDetails(user);
    }
}
