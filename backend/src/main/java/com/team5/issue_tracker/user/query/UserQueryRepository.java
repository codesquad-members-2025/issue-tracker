package com.team5.issue_tracker.user.query;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<UserSummaryResponse> findAllUsers() {
    String userSql = "SELECT id, username, imageUrl FROM user";
    return jdbcTemplate.query(userSql, (rs, rowNum) ->
        new UserSummaryResponse(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("imageUrl")
        )
    );
  }
}
