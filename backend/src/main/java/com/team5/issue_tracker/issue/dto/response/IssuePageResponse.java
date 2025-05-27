package com.team5.issue_tracker.issue.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssuePageResponse {
  private Integer total;
  private Integer page;
  private Integer perPage;
  private String q;
  private List<IssueSummaryResponse> issues;
}
