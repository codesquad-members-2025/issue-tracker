package com.team5.issue_tracker.issue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.issue.dto.IssuePageResponse;
import com.team5.issue_tracker.issue.query.IssueQueryService;
import com.team5.issue_tracker.issue.service.IssueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/issues")
public class IssueController {
  private final IssueService issueService;
  private final IssueQueryService issueQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<IssuePageResponse>> getAllIssues() {
    return ResponseEntity.ok(ApiResponse.success(issueQueryService.getIssuePage()));
  }
}
