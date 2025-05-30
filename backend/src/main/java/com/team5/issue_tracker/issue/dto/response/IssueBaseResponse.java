package com.team5.issue_tracker.issue.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueBaseResponse {
  private Long id;
  private String title;
  private Long authorId;
  private Long milestoneId;
  private Boolean isOpen;
  private Instant createdAt;
  private Instant updatedAt;
}
