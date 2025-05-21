package com.team5.issue_tracker.milestone.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MilestoneQueryRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<MilestoneSummaryResponse> findIssueMilestones() {
    String milestoneSql = "SELECT id, name FROM milestone";
    return jdbcTemplate.query(milestoneSql, (rs, rowNum) ->
        new MilestoneSummaryResponse(
            rs.getLong("id"),
            rs.getString("name")
        )
    );
  }

  public Map<Long, MilestoneSummaryResponse> getMilestonesByIds(List<Long> issueIds) {
    String milestoneSql = """
        SELECT 
            i.id AS issue_id,
            m.id AS milestone_id,
            m.name AS milestone_name
        FROM issue i
        JOIN milestone m ON i.milestone_id = m.id
        WHERE i.id IN (:issueIds)
        """;

    MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(milestoneSql, params);

    return rows.stream()
        .collect(Collectors.toMap(
            row -> ((Number) row.get("issue_id")).longValue(),
            row -> new MilestoneSummaryResponse(
                ((Number) row.get("milestone_id")).longValue(),
                (String) row.get("milestone_name")
            )
        ));
  }
}
