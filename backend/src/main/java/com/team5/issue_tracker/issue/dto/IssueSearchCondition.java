package com.team5.issue_tracker.issue.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //TODO: Setter를 나중에 빌더 패턴으로 변경할 것
@NoArgsConstructor
@AllArgsConstructor
public class IssueSearchCondition {
  private Boolean isOpen;
  private String assigneeName;
  private List<String> labelNames;
  private String milestoneName;
  private String authorName;
}
