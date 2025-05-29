package com.team5.issue_tracker.milestone.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneRequest {
  @NotBlank(message = "마일스톤 이름을 작성해주세요.")
  private String name;

  private LocalDate deadline;

  private String description;
}
