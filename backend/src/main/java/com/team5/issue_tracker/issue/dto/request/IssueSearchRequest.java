package com.team5.issue_tracker.issue.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // TODO: Setter 를 나중에 빌더 패턴으로 변경할 것
@NoArgsConstructor
@AllArgsConstructor
public class IssueSearchRequest {
  private Boolean isOpen;
  private String assigneeName;
  private Set<String> labelNames;
  private String milestoneName;
  private String authorName;

  public String toQueryString() {
    StringBuilder queryString = new StringBuilder();
    if (isOpen != null) {
      queryString.append("is:").append(isOpen ? "open" : "closed").append(" ");
    }

    if (assigneeName != null && !assigneeName.isEmpty()) {
      queryString.append("assignee:").append(assigneeName).append(" ");
    }

    if (labelNames != null && !labelNames.isEmpty()) {
      for (String labelName : labelNames) {
        queryString.append("label:").append(labelName).append(" ");
      }
    }

    if (milestoneName != null && !milestoneName.isEmpty()) {
      queryString.append("milestone:").append(milestoneName).append(" ");
    }

    if (authorName != null && !authorName.isEmpty()) {
      queryString.append("author:").append(authorName).append(" ");
    }

    return queryString.toString().trim();
  }
}
