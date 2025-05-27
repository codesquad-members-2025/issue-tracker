package com.team5.issue_tracker.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneCountResponse {
  private Long total;
  private Long openCount;
  private Long closedCount;
}
