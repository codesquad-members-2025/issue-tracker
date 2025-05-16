package com.team5.issue_tracker.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.team5.issue_tracker.label.dto.LabelResponse;
import com.team5.issue_tracker.milestone.dto.MilestoneResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueSummaryResponse {
  private Long id;
  private String title;
  private boolean isOpen;
  private List<LabelResponse> labels;
  private UserSummaryResponse author;
  private MilestoneResponse milestone;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long commentsCount;
}
