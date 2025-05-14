package com.team5.issue_tracker.issue.query;

import com.team5.issue_tracker.issue.dto.IssueSummaryResponse;
import com.team5.issue_tracker.label.dto.LabelResponse;
import com.team5.issue_tracker.milestone.dto.MilestoneResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class IssueQueryRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<IssueSummaryResponse> findAllIssues() {
    // 1. 이슈 목록 전체 조회
    String issueSql = """
        SELECT 
            i.id AS issue_id,
            i.title,
            i.is_open,
            i.created_at,
            i.updated_at,
            u.id AS user_id,
            u.username,
            u.image_url,
            m.id AS milestone_id,
            m.name AS milestone_title,
            (SELECT COUNT(*) FROM comment c WHERE c.issue_id = i.id) AS comments_count
        FROM issue i
        JOIN user u ON i.user_id = u.id
        LEFT JOIN milestone m ON i.milestone_id = m.id
        ORDER BY i.created_at DESC
        """;

    List<IssueSummaryResponse> issues =
        jdbcTemplate.query(issueSql, (rs, rowNum) -> mapToIssueListItem(rs));

    // 2. issue ID 목록 추출
    List<Long> issueIds = issues.stream().map(IssueSummaryResponse::getId).toList();
    if (issueIds.isEmpty()) {
      return issues;
    }

    // 3. 라벨 전체 조회 (IN 쿼리)
    Map<Long, List<LabelResponse>> labelMap = findLabelsByIssueIds(issueIds);

    // 4. 이슈에 라벨 붙이기
    for (IssueSummaryResponse issue : issues) {
      List<LabelResponse> labels = labelMap.getOrDefault(issue.getId(), List.of());
      issue.getLabels().addAll(labels); // 생성자에서 리스트 초기화해놔야 가능
    }

    return issues;
  }

  private IssueSummaryResponse mapToIssueListItem(ResultSet rs) throws SQLException {
    return new IssueSummaryResponse(
        rs.getLong("issue_id"),
        rs.getString("title"),
        rs.getBoolean("is_open"),
        new ArrayList<>(), // 라벨은 나중에 채움
        new UserSummaryResponse(
            rs.getLong("user_id"),
            rs.getString("username"),
            rs.getString("image_url")
        ),
        rs.getObject("milestone_id") != null
            ? new MilestoneResponse(
            rs.getLong("milestone_id"),
            rs.getString("milestone_title")
        )
            : null,
        rs.getTimestamp("created_at").toLocalDateTime(),
        rs.getTimestamp("updated_at").toLocalDateTime(),
        rs.getLong("comments_count")
    );
  }

  private Map<Long, List<LabelResponse>> findLabelsByIssueIds(List<Long> issueIds) {
    String sql = """
        SELECT il.issue_id, l.id AS label_id, l.name, l.color
        FROM issue_label il
        JOIN label l ON il.label_id = l.id
        WHERE il.issue_id IN (:issueIds)
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

    return rows.stream()
        .collect(Collectors.groupingBy(
            row -> ((Number) row.get("issue_id")).longValue(),
            Collectors.mapping(row -> new LabelResponse(
                ((Number) row.get("label_id")).longValue(),
                (String) row.get("name"),
                (String) row.get("color")
            ), Collectors.toList())
        ));
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
