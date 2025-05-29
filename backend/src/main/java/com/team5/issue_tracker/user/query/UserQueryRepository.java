package com.team5.issue_tracker.user.query;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.team5.issue_tracker.issue.dto.response.UserPreviewResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<UserSummaryResponse> getScrolledUsers(String cursor, Integer limit) {
    String userSql = """        
        SELECT id, username, image_url
        FROM user WHERE (:cursor IS NULL OR username > :cursor) 
        ORDER BY username ASC
        LIMIT :limitPlusOne;
        """;

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("cursor", cursor);
    params.addValue("limitPlusOne", limit + 1);

    return jdbcTemplate.query(userSql, params, (rs, rowNum) ->
        new UserSummaryResponse(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("image_url")
        )
    );
  }

  public UserSummaryResponse getAuthorById(Long userId) {
    String sql = """
            SELECT id, username, image_url
            FROM user
            WHERE id = :userId
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);

    return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> new UserSummaryResponse(
        rs.getLong("id"),
        rs.getString("username"),
        rs.getString("image_url")
    ));
  }

  public List<UserSummaryResponse> getAssigneesByIssueId(Long issueId) {
    String sql = """
            SELECT u.id, u.username, u.image_url
            FROM issue_assignee ia
            JOIN user u ON ia.assignee_id = u.id
            WHERE ia.issue_id = :issueId
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

    return jdbcTemplate.query(sql, params, (rs, rowNum) -> new UserSummaryResponse(
        rs.getLong("id"),
        rs.getString("username"),
        rs.getString("image_url")
    ));
  }

  public Map<Long, UserPreviewResponse> getAuthorsByIssueIds(List<Long> issueIds) {
    if (issueIds == null || issueIds.isEmpty()) {
      return Collections.emptyMap(); // 빈 결과 반환
    }

    String authorSql = """
        SELECT DISTINCT 
            i.id AS issue_id,
            u.id AS user_id, 
            u.username AS user_username
        FROM issue i
        JOIN user u ON i.user_id = u.id
        WHERE i.id IN (:issueIds)
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(authorSql, params);

    return rows.stream()
        .collect(Collectors.toMap(
            row -> ((Number) row.get("issue_id")).longValue(),
            row -> new UserPreviewResponse(
                ((Number) row.get("user_id")).longValue(),
                (String) row.get("user_username")
            )
        ));
  }

  public Map<Long, List<UserSummaryResponse>> getAssigneesByIssueIds(List<Long> issueIds) {
    if (issueIds == null || issueIds.isEmpty()) {
      return Collections.emptyMap(); // 빈 결과 반환
    }

    String assigneeSql = """
        SELECT
            i.id AS issue_id,
            u.id AS user_id,
            u.username AS user_username,
            u.image_url AS user_image_url
        FROM issue i
        INNER JOIN issue_assignee ia ON i.id = ia.issue_id
        INNER JOIN user u ON ia.assignee_id = u.id
        WHERE i.id IN (:issueIds)
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(assigneeSql, params);

    return rows.stream()
        .collect(Collectors.groupingBy(
            row -> ((Number) row.get("issue_id")).longValue(),
            Collectors.mapping(row -> new UserSummaryResponse(
                ((Number) row.get("user_id")).longValue(),
                (String) row.get("user_username"),
                (String) row.get("user_image_url")
            ), Collectors.toList())
        ));
  }

  public Long getUserIdByUsername(String username) {
    if (username == null) {
      return null;
    }
    String sql = "SELECT id FROM user WHERE username = :username";
    MapSqlParameterSource params = new MapSqlParameterSource("username", username);
    List<Long> result = jdbcTemplate.queryForList(sql, params, Long.class);

    return result.isEmpty() ? -1L : result.get(0);
  }
}
