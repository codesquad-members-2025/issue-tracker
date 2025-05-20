package com.team5.issue_tracker.issue.query;

import com.team5.issue_tracker.issue.dto.IssueQueryDto;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class IssueQueryRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<IssueQueryDto> findAllIssues() {
    String issueSql = """
        SELECT 
            i.id,
            i.title,
            i.is_open,
            i.created_at,
            i.updated_at
        FROM issue i
        ORDER BY i.created_at DESC
        """;
    return jdbcTemplate.query(issueSql, (rs, rowNum) ->
        new IssueQueryDto(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getBoolean("is_open"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
        )
    );
  }

  public List<UserSummaryResponse> findDistinctAuthors() {
    String authorSql = """
        SELECT DISTINCT u.id, u.username, u.image_url
        FROM issue i
        JOIN user u ON i.user_id = u.id
        """;

    return jdbcTemplate.query(authorSql, (rs, rowNum) ->
        new UserSummaryResponse(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("image_url")
        )
    );
  }
}
