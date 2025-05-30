package com.team5.issue_tracker.common.exception;

public class NotFoundException extends BaseException {
  public NotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public NotFoundException(ErrorCode errorCode, String customMessage) {
    super(errorCode, customMessage);
  }
}
