package com.team5.issue_tracker.issue.query;

import com.team5.issue_tracker.issue.dto.IssueQueryDto;
import com.team5.issue_tracker.issue.dto.IssueSearchCondition;
import com.team5.issue_tracker.issue.dto.response.IssueBaseResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class IssueQueryRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<IssueQueryDto> findIssuesByCondition(IssueSearchCondition searchCondition, Integer page,
      Integer perPage) {
    StringBuilder issueSql = new StringBuilder("""
            SELECT 
                i.id,
                i.title,
                i.is_open,
                i.created_at,
                i.updated_at
            FROM issue i
        """);

    List<String> whereClauses = new ArrayList<>();
    MapSqlParameterSource params = new MapSqlParameterSource();

    if (searchCondition.getIsOpen() != null) {
      whereClauses.add("i.is_open = :isOpen");
      params.addValue("isOpen", searchCondition.getIsOpen());
    }

    if (searchCondition.getAssigneeId() != null) {
      whereClauses.add("EXISTS (" +
          " SELECT 1 FROM issue_assignee ia" +
          " WHERE ia.issue_id = i.id" +
          " AND ia.assignee_id = :assigneeId" +
          ")"
      );
      params.addValue("assigneeId", searchCondition.getAssigneeId());
    }

    if (searchCondition.getLabelIds() != null && !searchCondition.getLabelIds().isEmpty()) {
      whereClauses.add("""
          i.id IN (
            SELECT issue_id
            FROM issue_label
            WHERE label_id IN (:labelIds)
            GROUP BY issue_id
            HAVING COUNT(DISTINCT label_id) = :labelCount
          )
          """);
      params.addValue("labelIds", searchCondition.getLabelIds());
      params.addValue("labelCount", searchCondition.getLabelIds().size());
    }

    if (searchCondition.getMilestoneId() != null) {
      whereClauses.add("i.milestone_id = :milestoneId");
      params.addValue("milestoneId", searchCondition.getMilestoneId());
    }

    if (searchCondition.getAuthorId() != null) {
      whereClauses.add("i.user_id = :authorId");
      params.addValue("authorId", searchCondition.getAuthorId());
    }

    if (!whereClauses.isEmpty()) {
      issueSql.append(" WHERE ").append(String.join(" AND ", whereClauses));
    }

    issueSql.append(" ORDER BY i.created_at DESC");

    int limit = perPage;
    int offset = (page - 1) * perPage;
    issueSql.append(" LIMIT :limit OFFSET :offset");
    params.addValue("limit", limit);
    params.addValue("offset", offset);

    return jdbcTemplate.query(issueSql.toString(), params, (rs, rowNum) ->
        new IssueQueryDto(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getBoolean("is_open"),
            rs.getTimestamp("created_at").toInstant(),
            rs.getTimestamp("updated_at").toInstant()
        )
    );
  }

  public List<UserSummaryResponse> findDistinctAuthors(String cursor, Integer limit) {
    String authorSql = """
        SELECT DISTINCT u.id, u.username, u.image_url
        FROM issue i
        JOIN user u ON i.user_id = u.id
        WHERE (:cursor IS NULL OR username > :cursor) 
        ORDER BY username ASC
        LIMIT :limitPlusOne;
        """;
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("cursor", cursor);
    params.addValue("limitPlusOne", limit + 1);

    return jdbcTemplate.query(authorSql, params, (rs, rowNum) ->
        new UserSummaryResponse(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("image_url")
        )
    );
  }

  public IssueBaseResponse findIssueDetailById(Long issueId) {
    String issueSql = """
            SELECT id, title, user_id, milestone_id, is_open, created_at, updated_at
            FROM issue
            WHERE id = :issueId
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

    return jdbcTemplate.queryForObject(issueSql, params, (rs, rowNum) -> new IssueBaseResponse(
        rs.getLong("id"),
        rs.getString("title"),
        rs.getLong("user_id"),
        rs.getLong("milestone_id"),
        rs.getBoolean("is_open"),
        rs.getTimestamp("created_at").toInstant(),
        rs.getTimestamp("updated_at").toInstant()
    ));
  }
}
