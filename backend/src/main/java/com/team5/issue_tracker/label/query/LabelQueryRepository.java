package com.team5.issue_tracker.label.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.team5.issue_tracker.label.dto.LabelResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LabelQueryRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public Map<Long, List<LabelResponse>> getLabelListByIssueIds(List<Long> issueIds) {
    String labelSql = """
        SELECT 
            i.id AS issue_id,
            l.id AS label_id,
            l.name AS label_name,
            l.color AS label_color
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
            Collectors.mapping(row -> new LabelResponse(
                ((Number) row.get("label_id")).longValue(),
                (String) row.get("name"),
                (String) row.get("color")
            ), Collectors.toList())
        ));
  }
}
