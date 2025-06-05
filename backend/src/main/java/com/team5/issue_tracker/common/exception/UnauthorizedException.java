package com.team5.issue_tracker.common.exception;

public class UnauthorizedException extends BaseException {
  public UnauthorizedException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UnauthorizedException(ErrorCode errorCode, String customMessage) {
    super(errorCode, customMessage);
  }
}
