package com.team5.issue_tracker.issue.dto.response;

import java.util.List;

import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueLabelScrollResponse {
  private Boolean hasNext;
  private String nextCursor;
  private List<LabelSummaryResponse> labels;
}
