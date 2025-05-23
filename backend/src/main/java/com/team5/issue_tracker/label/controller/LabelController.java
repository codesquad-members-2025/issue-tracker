package com.team5.issue_tracker.label.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.label.dto.response.LabelPageResponse;
import com.team5.issue_tracker.label.query.LabelQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/labels")
public class LabelController {
  private final LabelQueryService labelQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<LabelPageResponse>> getAllLabels() {
    return ResponseEntity.ok(ApiResponse.success(labelQueryService.getAllLabels()));
  }
}
