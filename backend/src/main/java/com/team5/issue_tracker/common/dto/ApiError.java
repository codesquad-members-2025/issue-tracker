package com.team5.issue_tracker.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiError {
  private final String message;
  private final int code;
}
