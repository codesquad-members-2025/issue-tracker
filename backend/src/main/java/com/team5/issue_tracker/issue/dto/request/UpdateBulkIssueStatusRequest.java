package com.team5.issue_tracker.issue.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBulkIssueStatusRequest {
  @NotNull
  @Size(min = 1)
  private List<Long> issueIds;

  @NotNull
  private Boolean isOpen;
}
