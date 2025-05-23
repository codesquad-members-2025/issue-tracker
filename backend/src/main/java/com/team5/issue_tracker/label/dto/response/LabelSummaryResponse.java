package com.team5.issue_tracker.label.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelSummaryResponse {
  private Long id;
  private String name;
  private String textColor;
  private String backgroundColor;
}
