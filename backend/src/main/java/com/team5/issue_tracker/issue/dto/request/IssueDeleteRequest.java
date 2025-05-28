package com.team5.issue_tracker.issue.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDeleteRequest {
  @NotEmpty(message = "삭제할 이슈 ID를 선택해주세요.")
  private List<Long> issueIds;
}
