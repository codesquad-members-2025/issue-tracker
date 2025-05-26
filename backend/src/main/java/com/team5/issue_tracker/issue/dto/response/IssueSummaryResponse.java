package com.team5.issue_tracker.issue.dto.response;

import java.time.Instant;
import java.util.List;

import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;
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
  private Boolean isOpen;
  private List<LabelSummaryResponse> labels;
  private UserSummaryResponse author;
  private MilestoneSummaryResponse milestone;
  private Instant createdAt;
  private Instant updatedAt;
  private Long commentsCount;
}
