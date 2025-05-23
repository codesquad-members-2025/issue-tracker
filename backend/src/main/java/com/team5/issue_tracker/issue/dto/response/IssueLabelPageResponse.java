package com.team5.issue_tracker.issue.dto.response;

import java.util.List;

import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueLabelPageResponse {
  private Long total;
  private Long page;
  private Long perPage;
  private List<LabelSummaryResponse> labels;
}
