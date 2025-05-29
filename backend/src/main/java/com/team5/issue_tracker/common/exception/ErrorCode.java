package com.team5.issue_tracker.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  // 유저 관련
  USER_NOT_FOUND(404, "USER_NOT_FOUND", "해당 유저를 찾을 수 없습니다."),
  USER_ALREADY_EXISTS(409, "USER_ALREADY_EXISTS", "이미 존재하는 유저입니다."),
  // 이슈 관련
  ISSUE_NOT_FOUND(404, "ISSUE_NOT_FOUND", "해당 이슈를 찾을 수 없습니다."),
  // 인증 관련
  UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),
  // 서버 오류
  INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버에 오류가 발생했습니다.");

  private final int status;
  private final String code;
  private final String message;

  ErrorCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
