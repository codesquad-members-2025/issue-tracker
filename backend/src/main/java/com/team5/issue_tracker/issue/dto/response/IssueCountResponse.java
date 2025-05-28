package com.team5.issue_tracker.issue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCountResponse {
  private Long openCount;
  private Long closedCount;
}
