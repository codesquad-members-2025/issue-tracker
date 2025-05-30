package com.team5.issue_tracker.common.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.common.dto.PresignedUrlResponse;
import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.PayloadTooLargeException;
import com.team5.issue_tracker.common.service.S3UploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/upload-url")
@RequiredArgsConstructor
public class S3UploadController {

  private final S3UploadService s3UploadService;
  private static final Long MAX_SIZE = 5 * 1024 * 1024L; // 5MB

  @GetMapping
  public ResponseEntity<ApiResponse<PresignedUrlResponse>> getPresignedUrl(
      @RequestParam String filename,
      @RequestParam String type,
      @RequestParam Long size
  ) {
    if (size > MAX_SIZE) {
      throw new PayloadTooLargeException(ErrorCode.PAYLOAD_TOO_LARGE);
    }

    String key = type + "-images/" + UUID.randomUUID() + "_" + filename;
    String uploadUrl = s3UploadService.createPresignedUrl(key);
    String accessUrl = s3UploadService.getAccessUrl(key);

    return ResponseEntity.ok(ApiResponse.success(new PresignedUrlResponse(uploadUrl, accessUrl)));
  }
}
