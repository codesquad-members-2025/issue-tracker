package com.team5.issue_tracker.common.comment.query;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.team5.issue_tracker.common.comment.dto.CommentResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<CommentResponse> getCommentsByIssueId(Long issueId) {
    String sql = """
        SELECT 
            c.id AS comment_id,
            c.content AS body,
            c.created_at,
            c.updated_at,
            u.id AS user_id,
            u.username,
            u.image_url
        FROM comment c
        LEFT JOIN user u ON c.user_id = u.id
        WHERE c.issue_id = :issueId
        ORDER BY c.created_at ASC
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

    return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
      UserSummaryResponse author = new UserSummaryResponse(
          rs.getLong("user_id"),
          rs.getString("username"),
          rs.getString("image_url")
      );

      return new CommentResponse(
          rs.getLong("comment_id"),
          rs.getString("body"),
          author,
          rs.getTimestamp("created_at").toInstant(),
          rs.getTimestamp("updated_at").toInstant()
      );
    });
  }
}
