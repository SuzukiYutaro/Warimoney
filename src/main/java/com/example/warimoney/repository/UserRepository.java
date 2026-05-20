package com.example.warimoney.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.warimoney.domain.User;

@Repository
public class UserRepository {

  @Autowired
  private NamedParameterJdbcTemplate template;

  public User save(User user) {
    String sql = "INSERT INTO users(username, password, role) VALUES(:username, :password, :role)";
    MapSqlParameterSource param = new MapSqlParameterSource()
        .addValue("username", user.username())
        .addValue("password", user.password())
        .addValue("role", user.role());

    // NamedParameterJdbcTemplate は直接 KeyHolder を取れないため、PreparedStatementCreator を使う
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.getJdbcTemplate().update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO users(username, password, role) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, user.username());
      ps.setString(2, user.password());
      ps.setString(3, user.role());
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    int id = (key != null) ? key.intValue() : 0;
    return new User(id, user.username(), user.password(), user.role());
  }
}
