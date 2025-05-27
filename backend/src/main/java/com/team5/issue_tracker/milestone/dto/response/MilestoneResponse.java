package com.team5.issue_tracker.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneResponse {
  private Long id;
  private String name;
  private String description;
  private LocalDate deadline;
  private Boolean isOpen;
  private Long openIssueCount;
  private Long closedIssueCount;
  private Long progress;
}
