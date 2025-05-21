package com.team5.issue_tracker.label.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.team5.issue_tracker.label.dto.response.LabelResponse;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LabelQueryRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<LabelSummaryResponse> findIssueLabels() {
    String lableSql = "SELECT id, name, text_color, background_color FROM label";
    return jdbcTemplate.query(lableSql, (rs, rowNum) ->
        new LabelSummaryResponse(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("text_color"),
            rs.getString("background_color")
        )
    );
  }

  public List<LabelResponse> findAllLabels() {
    String lableSql = "SELECT id, name, description, text_color, background_color FROM label";
    return jdbcTemplate.query(lableSql, (rs, rowNum) ->
        new LabelResponse(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getString("text_color"),
            rs.getString("background_color")
        )
    );
  }

  public Map<Long, List<LabelSummaryResponse>> getLabelListByIssueIds(List<Long> issueIds) {
    String labelSql = """
        SELECT 
            i.id AS issue_id,
            l.id AS label_id,
            l.name AS label_name,
            l.text_color AS label_text_color,
            l.background_color AS label_background_color
        FROM issue i
        JOIN issue_label il ON i.id = il.issue_id
        JOIN label l ON il.label_id = l.id
        WHERE i.id IN (:issueIds)
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(labelSql, params);

    return rows.stream()
        .collect(Collectors.groupingBy(
            row -> ((Number) row.get("issue_id")).longValue(),
            Collectors.mapping(row -> new LabelSummaryResponse(
                ((Number) row.get("label_id")).longValue(),
                (String) row.get("label_name"),
                (String) row.get("label_text_color"),
                (String) row.get("label_background_color")
            ), Collectors.toList())
        ));
  }
}
