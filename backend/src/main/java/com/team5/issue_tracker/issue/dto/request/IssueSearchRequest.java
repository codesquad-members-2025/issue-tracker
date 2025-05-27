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
      queryString.append("assignee:").append(quoteIfContainsWhitespace(assigneeName)).append(" ");
    }

    if (labelNames != null && !labelNames.isEmpty()) {
      for (String labelName : labelNames) {
        queryString.append("label:").append(quoteIfContainsWhitespace(labelName)).append(" ");
      }
    }

    if (milestoneName != null && !milestoneName.isEmpty()) {
      queryString.append("milestone:").append(quoteIfContainsWhitespace(milestoneName)).append(" ");
    }

    if (authorName != null && !authorName.isEmpty()) {
      queryString.append("author:").append(quoteIfContainsWhitespace(authorName)).append(" ");
    }

    return queryString.toString().trim();
  }

  private String quoteIfContainsWhitespace(String value) {
    if (value.contains(" ")) {
      return "\"" + value + "\"";
    }
    return value;
  }
}
