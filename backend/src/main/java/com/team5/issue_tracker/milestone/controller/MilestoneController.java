package com.team5.issue_tracker.milestone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestonePageResponse;
import com.team5.issue_tracker.milestone.query.MilestoneQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {
  private final MilestoneQueryService milestoneQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<MilestonePageResponse>> getAllLabels() {
    return ResponseEntity.ok(ApiResponse.success(milestoneQueryService.getAllMilestones()));
  }
}
