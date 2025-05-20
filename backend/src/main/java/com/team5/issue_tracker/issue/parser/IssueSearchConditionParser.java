package com.team5.issue_tracker.issue.parser;

import java.util.ArrayList;

import com.team5.issue_tracker.issue.dto.IssueSearchCondition;

public class IssueSearchConditionParser {
  public static IssueSearchCondition fromQueryString(String q) {
    IssueSearchCondition issueSearchCondition = new IssueSearchCondition();
    issueSearchCondition.setLabelNames(new ArrayList<>());

    String[] parts = q.trim().split("\\s+"); // 공백으로 구분

    for (String part : parts) {
      String[] pair = part.split(":", 2); // key:value
      if (pair.length != 2) {
        continue; // 잘못된 형식은 무시
      }
      String key = pair[0].trim();
      String value = pair[1].trim();
      switch (key) {
        case "is" -> {
          if (!value.equalsIgnoreCase("open") && !value.equalsIgnoreCase("closed")) {
            continue;
          }
          issueSearchCondition.setIsOpen(value.equalsIgnoreCase("open"));
        }
        case "assignee" -> issueSearchCondition.setAssigneeName(value); //TODO: 나중에 빌더로 변경하면 편할듯
        case "label" -> issueSearchCondition.getLabelNames().add(value);
        case "milestone" -> issueSearchCondition.setMilestoneName(value);
        case "author" -> issueSearchCondition.setAuthorName(value);
        default -> {
          continue;
        }
      }
    }

    return issueSearchCondition;
  }
}
