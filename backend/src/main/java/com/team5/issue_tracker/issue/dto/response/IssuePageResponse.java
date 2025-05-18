package com.team5.issue_tracker.issue.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssuePageResponse {
  private Long total;
  private Long page;
  private Long perPage;
  private List<IssueSummaryResponse> issues;
}
