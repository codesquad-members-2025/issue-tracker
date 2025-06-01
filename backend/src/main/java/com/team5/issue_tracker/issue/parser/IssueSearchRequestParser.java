package com.team5.issue_tracker.issue.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.team5.issue_tracker.issue.dto.request.IssueSearchRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IssueSearchRequestParser {

  public enum QueryKey {
    IS("is", (value, req) -> {
      if (value.equalsIgnoreCase("open") || value.equalsIgnoreCase("closed")) {
        req.setIsOpen(value.equalsIgnoreCase("open"));
      }
    }),
    ASSIGNEE("assignee", (value, req) -> req.setAssigneeName(value)),
    LABEL("label", (value, req) -> req.getLabelNames().add(value)),
    MILESTONE("milestone", (value, req) -> req.setMilestoneName(value)),
    AUTHOR("author", (value, req) -> req.setAuthorName(value));

    private final String key;
    private final BiConsumer<String, IssueSearchRequest> handler;

    QueryKey(String key, BiConsumer<String, IssueSearchRequest> handler) {
      this.key = key;
      this.handler = handler;
    }

    public void apply(String value, IssueSearchRequest request) {
      handler.accept(value, request);
    }

    public static Optional<QueryKey> from(String key) {
      return Arrays.stream(values())
          .filter(qk -> qk.key.equalsIgnoreCase(key))
          .findFirst();
    }
  }

  public static IssueSearchRequest fromQueryString(String q) {
    IssueSearchRequest issueSearchRequest = new IssueSearchRequest();
    issueSearchRequest.setLabelNames(new HashSet<>());

    if (q == null || q.isEmpty()) {
      return issueSearchRequest; // 쿼리 문자열이 비어있으면 기본값 반환
    }

    List<String> parts = parseQueryToParts(q);

    for (String part : parts) {
      Optional<Map.Entry<String, String>> keyValuePair = parseKeyValuePair(part);
      if (keyValuePair.isEmpty()) {
        continue; // 잘못된 형식은 무시
      }
      Map.Entry<String, String> entry = keyValuePair.get();
      String key = entry.getKey();
      String value = entry.getValue();

      QueryKey.from(key).ifPresent(
          qk -> qk.apply(value, issueSearchRequest)
      );
    }
    log.debug("assigneeName: {}, labelNames: {}, milestoneName: {}, authorName: {}",
        issueSearchRequest.getAssigneeName(),
        issueSearchRequest.getLabelNames(),
        issueSearchRequest.getMilestoneName(),
        issueSearchRequest.getAuthorName());
    return issueSearchRequest;
  }

  private static List<String> parseQueryToParts(String q) {
    List<String> parts = new ArrayList<>();
    Matcher matcher = Pattern.compile("(\\w+:\"[^\"]*\"|\\w+:\\S+)").matcher(q);
    while (matcher.find()) {
      parts.add(matcher.group());
    }
    return parts;
  }

  private static Optional<Map.Entry<String, String>> parseKeyValuePair(String part) {
    String[] pair = part.split(":", 2);
    if (pair.length != 2) {
      return Optional.empty(); // 잘못된 형식은 무시
    }
    String key = pair[0].trim();
    String value = pair[1].trim();

    if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
      value = value.substring(1, value.length() - 1).trim(); // 따옴표 안쪽 공백도 제거
    }

    if (value.isEmpty()) {
      return Optional.empty(); // value가 비어있으면 무시
    }
    return Optional.of(Map.entry(key, value));
  }
}
