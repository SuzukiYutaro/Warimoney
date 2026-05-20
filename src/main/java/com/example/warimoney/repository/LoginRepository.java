package com.example.warimoney.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.warimoney.domain.User;

@Repository
public class LoginRepository {

  @Autowired
  private NamedParameterJdbcTemplate template;

  private RowMapper<User> USER_ROW_MAPPER = (rs, row) -> new User(
      rs.getInt("id"),
      rs.getString("username"),
      rs.getString("password"),
      rs.getString("role"));

  public Optional<User> findByUsername(String username) {
    SqlParameterSource param = new MapSqlParameterSource().addValue("username", username);
    String sql = "SELECT * FROM users WHERE username = :username";

    // queryForObject は結果が0件のとき例外を投げるため、安全に扱う
    List<User> users = template.query(sql, param, USER_ROW_MAPPER);
    if (users == null || users.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(users.get(0));
  }
}