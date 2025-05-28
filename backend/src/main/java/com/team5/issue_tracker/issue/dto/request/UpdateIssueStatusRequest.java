package com.team5.issue_tracker.issue.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIssueStatusRequest {
  @NotNull(message = "isOpen 값은 필수입니다.")
  private Boolean isOpen;
}
