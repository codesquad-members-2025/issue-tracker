package com.team5.issue_tracker.label.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.label.dto.request.LabelRequest;
import com.team5.issue_tracker.label.dto.response.LabelPageResponse;
import com.team5.issue_tracker.label.query.LabelQueryService;
import com.team5.issue_tracker.label.service.LabelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/labels")
public class LabelController {
  private final LabelService labelService;
  private final LabelQueryService labelQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<LabelPageResponse>> getPagedLabels(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer perPage) {
    return ResponseEntity.ok(ApiResponse.success(labelQueryService.getPagedLabels(page, perPage)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createLabel(
      @Valid @RequestBody LabelRequest request
  ) {
    return ResponseEntity.ok(ApiResponse.success(labelService.createLabel(request)));
  }

  @PutMapping("/{labelId}")
  public ResponseEntity<Void> updateLabel(
      @RequestParam Long labelId,
      @Valid @RequestBody LabelRequest request
  ) {
    labelService.updateLabel(labelId, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{labelId}")
  public ResponseEntity<Void> deleteLabel(
      @RequestParam Long labelId
  ){
    labelService.deleteLabel(labelId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/count")
  public ResponseEntity<ApiResponse<Long>> getLabelCount() {
    Long count = labelQueryService.getLabelCount();
    return ResponseEntity.ok(ApiResponse.success(count));
  }
}
