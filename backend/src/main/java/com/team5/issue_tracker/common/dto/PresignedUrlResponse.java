package com.team5.issue_tracker.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PresignedUrlResponse {
  private String uploadUrl;
  private String accessUrl;
}
