package com.team5.issue_tracker.issue.dto.response;

import java.util.List;

import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueMilestoneScrollResponse {
  private Boolean hasNext;
  private String nextCursor;
  private List<MilestoneSummaryResponse> milestones;
}
