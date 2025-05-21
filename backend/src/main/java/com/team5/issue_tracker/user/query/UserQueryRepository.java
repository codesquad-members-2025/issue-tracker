package com.team5.issue_tracker.user.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<UserSummaryResponse> findAllUsers() {
    String userSql = "SELECT id, username, image_url FROM user";
    return jdbcTemplate.query(userSql, (rs, rowNum) ->
        new UserSummaryResponse(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("image_url")
        )
    );
  }

  public Map<Long, UserSummaryResponse> getAuthorsByIssueIds(List<Long> issueIds) {
    String authorSql = """
        SELECT DISTINCT 
            i.id AS issue_id,
            u.id AS user_id, 
            u.username AS user_username, 
            u.image_url AS user_image_url
        FROM issue i
        JOIN user u ON i.user_id = u.id
        WHERE i.id IN (:issueIds)
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(authorSql, params);

    return rows.stream()
        .collect(Collectors.toMap(
            row -> ((Number) row.get("issue_id")).longValue(),
            row -> new UserSummaryResponse(
                ((Number) row.get("user_id")).longValue(),
                (String) row.get("user_username"),
                (String) row.get("user_image_url")
            )
        ));
  }
}
