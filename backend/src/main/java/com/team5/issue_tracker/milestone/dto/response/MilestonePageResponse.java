package com.team5.issue_tracker.milestone.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MilestonePageResponse {
  private Integer total;
  private Integer page;
  private Integer perPage;
  private Long OpenCount;
  private Long ClosedCount;
  private List<MilestoneResponse> milestones;
}
