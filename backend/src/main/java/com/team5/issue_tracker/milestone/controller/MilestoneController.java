package com.team5.issue_tracker.milestone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.milestone.dto.request.MilestoneCreateRequest;
import com.team5.issue_tracker.milestone.dto.response.MilestonePageResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.team5.issue_tracker.milestone.query.MilestoneQueryService;
import com.team5.issue_tracker.milestone.service.MilestoneService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {
  private final MilestoneService milestoneService;
  private final MilestoneQueryService milestoneQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<MilestonePageResponse>> getPagedMilestones(
      @RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "perPage", defaultValue = "10") Integer perPage
  ) {
    return ResponseEntity.ok(ApiResponse.success(milestoneQueryService.getPagedMilestones(page, perPage)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createMilestone(
      @Valid @RequestBody MilestoneCreateRequest request
  ) {
    return ResponseEntity.ok(ApiResponse.success(milestoneService.createMilestone(request)));
  }

  @GetMapping("/id")
  public ResponseEntity<ApiResponse<MilestoneResponse>> getMilestoneById(@RequestParam Long id) {
    return ResponseEntity.ok(ApiResponse.success(milestoneQueryService.getMilestoneById(id)));
  }

  @DeleteMapping("/id")
  public ResponseEntity<Void> deleteMilestoneById(@RequestParam Long id) {
    milestoneService.deleteMilestoneById(id);
    return  ResponseEntity.noContent().build();
  }

  @GetMapping("/count")
  public ResponseEntity<ApiResponse<Long>> getMilestoneCount() {
    Long count = milestoneQueryService.getMilestoneCount();
    return ResponseEntity.ok(ApiResponse.success(count));
  }
}
