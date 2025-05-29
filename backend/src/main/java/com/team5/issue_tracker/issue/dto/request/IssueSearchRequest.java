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

    appendCondition(queryString, "is", isOpen != null ? (isOpen ? "open" : "closed") : null);
    appendCondition(queryString, "assignee", assigneeName);
    for (String labelName : labelNames) {
      appendCondition(queryString, "label", labelName);
    }
    appendCondition(queryString, "milestone", milestoneName);
    appendCondition(queryString, "author", authorName);
    return queryString.toString().trim();
  }

  private String quoteIfContainsWhitespace(String value) {
    if (value.contains(" ")) {
      return "\"" + value + "\"";
    }
    return value;
  }

  private void appendCondition(StringBuilder queryString, String key, String value) {
    if (value != null && !value.isEmpty()) {
      queryString.append(key).append(":").append(quoteIfContainsWhitespace(value)).append(" ");
    }
  }
}
