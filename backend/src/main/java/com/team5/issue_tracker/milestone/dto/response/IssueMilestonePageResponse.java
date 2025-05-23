package com.team5.issue_tracker.milestone.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueMilestonePageResponse {
  private Long total;
  private Long page;
  private Long perPage;
  private List<MilestoneSummaryResponse> milestones;
}
