package com.team5.issue_tracker.issue.dto;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterSql {
  private String sql;
  private MapSqlParameterSource params;
}
