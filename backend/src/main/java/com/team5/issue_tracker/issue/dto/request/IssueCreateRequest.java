package com.team5.issue_tracker.issue.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreateRequest {
  private String title;
  private String body;
  private Long assignee;
  private List<Long> labels;
  private Long milestone;
}
