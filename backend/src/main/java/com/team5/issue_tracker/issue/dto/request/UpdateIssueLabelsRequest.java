package com.team5.issue_tracker.issue.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIssueLabelsRequest {
  private Set<Long> labelIds;
}
