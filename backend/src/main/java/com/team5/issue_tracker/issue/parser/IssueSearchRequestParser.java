package com.team5.issue_tracker.issue.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.team5.issue_tracker.issue.dto.request.IssueSearchRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IssueSearchRequestParser {
  public static IssueSearchRequest fromQueryString(String q) {
    IssueSearchRequest issueSearchRequest = new IssueSearchRequest();
    issueSearchRequest.setLabelNames(new HashSet<>());

    if (q == null || q.isEmpty()) {
      return issueSearchRequest; // 쿼리 문자열이 비어있으면 기본값 반환
    }

    List<String> parts = new ArrayList<>();
    Matcher matcher = Pattern.compile("(\\w+:\"[^\"]*\"|\\w+:\\S+)").matcher(q);
    while (matcher.find()) {
      parts.add(matcher.group());
    }

    for (String part : parts) {
      String[] pair = part.split(":", 2); // key:value
      if (pair.length != 2) {
        continue; // 잘못된 형식은 무시
      }
      String key = pair[0].trim();
      String value = pair[1].trim();
      if (value.isEmpty()) {
        continue; // value가 비어있으면 무시
      }
      if (value.startsWith("\"") && value.endsWith("\"")) { // 따옴표로 감싸진 경우 따옴표 제거
        value = value.substring(1, value.length() - 1);
      }
      switch (key) {
        case "is" -> {
          if (!value.equalsIgnoreCase("open") && !value.equalsIgnoreCase("closed")) {
            continue;
          }
          issueSearchRequest.setIsOpen(value.equalsIgnoreCase("open"));
        }
        case "assignee" -> issueSearchRequest.setAssigneeName(value); //TODO: 나중에 빌더로 변경하면 편할듯
        case "label" -> issueSearchRequest.getLabelNames().add(value);
        case "milestone" -> issueSearchRequest.setMilestoneName(value);
        case "author" -> issueSearchRequest.setAuthorName(value);
        default -> {
          continue;
        }
      }
    }
    log.debug("assigneeName: {}, labelNames: {}, milestoneName: {}, authorName: {}",
        issueSearchRequest.getAssigneeName(),
        issueSearchRequest.getLabelNames(),
        issueSearchRequest.getMilestoneName(),
        issueSearchRequest.getAuthorName());

    return issueSearchRequest;
  }
}
