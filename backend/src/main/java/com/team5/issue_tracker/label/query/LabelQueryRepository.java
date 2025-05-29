package com.team5.issue_tracker.label.query;

import java.util.Collection;
import java.util.Collections;
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

  public List<LabelSummaryResponse> findIssueLabels(String cursor, Integer limit) {
    String lableSql = """
        SELECT id, name, text_color, background_color FROM label
        WHERE (:cursor IS NULL OR name > :cursor)
        ORDER BY name ASC
        LIMIT :limitPlusOne;
        """;
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("cursor", cursor);
    params.addValue("limitPlusOne", limit + 1);

    return jdbcTemplate.query(lableSql, params, (rs, rowNum) ->
        new LabelSummaryResponse(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("text_color"),
            rs.getString("background_color")
        )
    );
  }

  public List<LabelResponse> findLabels(Integer page, Integer perPage) {
    String lableSql =
        "SELECT id, name, description, text_color, background_color FROM label LIMIT :limit OFFSET :offset";

    MapSqlParameterSource params = new MapSqlParameterSource();

    int limit = perPage;
    int offset = (page - 1) * perPage;
    params.addValue("limit", limit);
    params.addValue("offset", offset);

    return jdbcTemplate.query(lableSql, params, (rs, rowNum) ->
        new LabelResponse(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getString("text_color"),
            rs.getString("background_color")
        )
    );
  }

  public List<LabelResponse> getLabelsByIssueId(Long issueId) {
    String sql = """
        SELECT 
            l.id, l.name, l.description, l.text_color, l.background_color
        FROM issue_label il
        JOIN label l ON il.label_id = l.id
        WHERE il.issue_id = :issueId
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueId", issueId);

    return jdbcTemplate.query(sql, params, (rs, rowNum) -> new LabelResponse(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getString("description"),
        rs.getString("text_color"),
        rs.getString("background_color")
    ));
  }

  public Map<Long, List<LabelResponse>> getLabelListByIssueIds(List<Long> issueIds) {
    if (issueIds == null || issueIds.isEmpty()) {
      return Collections.emptyMap(); // 빈 결과 반환
    }

    String labelSql = """
        SELECT 
            i.id AS issue_id,
            l.id AS label_id,
            l.name AS label_name,
            l.description AS label_description,
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
            Collectors.mapping(row -> new LabelResponse(
                ((Number) row.get("label_id")).longValue(),
                (String) row.get("label_name"),
                (String) row.get("label_description"),
                (String) row.get("label_text_color"),
                (String) row.get("label_background_color")
            ), Collectors.toList())
        ));
  }

  public List<Long> getLabelIdsByNames(Collection<String> issueNames) {
    if (issueNames.isEmpty()) {
      return List.of();
    }
    String sql = "SELECT id FROM label WHERE name IN (:issueNames)";
    MapSqlParameterSource params = new MapSqlParameterSource("issueNames", issueNames);
    List<Long> result = jdbcTemplate.queryForList(sql, params, Long.class);

    return result.isEmpty() ? List.of(-1L) : result;
  }
}
