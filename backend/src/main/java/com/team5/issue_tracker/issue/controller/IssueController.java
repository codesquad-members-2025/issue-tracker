package com.team5.issue_tracker.issue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.issue.dto.request.IssueCreateRequest;
import com.team5.issue_tracker.issue.dto.response.IssuePageResponse;
import com.team5.issue_tracker.issue.query.IssueQueryService;
import com.team5.issue_tracker.issue.service.IssueService;
import com.team5.issue_tracker.label.dto.response.IssueLabelPageResponse;
import com.team5.issue_tracker.label.query.LabelQueryService;
import com.team5.issue_tracker.milestone.dto.response.IssueMilestonePageResponse;
import com.team5.issue_tracker.milestone.query.MilestoneQueryService;
import com.team5.issue_tracker.user.dto.UserPageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/issues")
public class IssueController {
  private final IssueService issueService;
  private final IssueQueryService issueQueryService;
  private final LabelQueryService labelQueryService;
  private final MilestoneQueryService milestonePageResponse;

  @GetMapping
  public ResponseEntity<ApiResponse<IssuePageResponse>> getAllIssues() {
    log.info("GET /api/issues 요청");
    return ResponseEntity.ok(ApiResponse.success(issueQueryService.getIssuePage()));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createIssue(
      @Valid @RequestBody IssueCreateRequest request
  ) {
    log.info("GET /api/issues 요청");
    return ResponseEntity.ok(ApiResponse.success(issueService.createIssue(request)));
  }

  @GetMapping("/authors")
  public ResponseEntity<ApiResponse<UserPageResponse>> getAllAuthors() {
    log.info("GET /api/issues/authors 요청");
    return ResponseEntity.ok(ApiResponse.success(issueQueryService.getIssueAuthors()));
  }

  @GetMapping("/labels")
  public ResponseEntity<ApiResponse<IssueLabelPageResponse>> getFilterLabels() {
    return ResponseEntity.ok(ApiResponse.success(labelQueryService.getFilterLabels()));
  }

  @GetMapping("/milestones")
  public ResponseEntity<ApiResponse<IssueMilestonePageResponse>> getFilterMilestones() {
    return ResponseEntity.ok(ApiResponse.success(milestonePageResponse.getFilterMilestones()));
  }

}
