package com.team5.issue_tracker.issue.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIssueTitleRequest {
  @NotBlank(message = "제목을 작성해주세요.")
  String title;
}
