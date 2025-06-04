package com.team5.issue_tracker.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  // 유저 관련
  USER_NOT_FOUND(404, "USER_NOT_FOUND", "해당 유저를 찾을 수 없습니다."),
  USER_ALREADY_EXISTS(409, "USER_ALREADY_EXISTS", "이미 존재하는 유저입니다."),
  EMAIL_ALREADY_EXISTS(409, "EMAIL_ALREADY_EXISTS", "이미 존재하는 이메일입니다."),
  USERNAME_ALREADY_EXISTS(409, "USERNAME_ALREADY_EXISTS", "이미 존재하는 유저이름입니다."),
  // 코멘트 관련
  COMMENT_NOT_FOUND(404, "COMMENT_NOT_FOUND", "해당 댓글을 찾을 수 없습니다."),
  // 라벨 관련
  LABEL_NOT_FOUND(404, "LABEL_NOT_FOUND", "해당 라벨을 찾을 수 없습니다."),
  // 마일스톤 관련
  MILESTONE_NOT_FOUND(404, "MILESTONE_NOT_FOUND", "해당 마일스톤을 찾을 수 없습니다."),
  // 이슈 관련
  ISSUE_NOT_FOUND(404, "ISSUE_NOT_FOUND", "해당 이슈를 찾을 수 없습니다."),
  // 인증 관련
  UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),
  INVALID_PASSWORD(401, "INVALID_PASSWORD", "잘못된 비밀번호입니다."),
  INVALID_GITHUB_CODE(401, "INVALID_GITHUB_CODE", "유효하지 않은 GitHub 코드입니다."),
  // 서버 오류
  INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버에 오류가 발생했습니다."),
  // 파일 관련
  PAYLOAD_TOO_LARGE(413, "PAYLOAD_TOO_LARGE", "파일크기가 혀용된 최대 크기를 초과하였습니다.");


  private final int status;
  private final String code;
  private final String message;

  ErrorCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
