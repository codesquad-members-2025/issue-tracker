package com.team5.issue_tracker.issue.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueSearchCondition {
  private Boolean isOpen;
  private String assigneeName;
  private List<String> labelNames;
  private String milestoneName;
  private String authorName;
}
