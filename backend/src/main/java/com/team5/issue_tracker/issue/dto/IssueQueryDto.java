package com.team5.issue_tracker.issue.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueQueryDto {
  private Long id;
  private String title;
  private boolean isOpen;
  private Instant createdAt;
  private Instant updatedAt;
}
