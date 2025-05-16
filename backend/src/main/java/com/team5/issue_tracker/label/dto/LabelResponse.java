package com.team5.issue_tracker.label.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelResponse {
  private Long id;
  private String name;
  private String color;
}
