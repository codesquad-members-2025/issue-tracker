package com.team5.issue_tracker.common.exception;

public class PayloadTooLargeException extends BaseException {
  public PayloadTooLargeException(ErrorCode errorCode) {
    super(errorCode);
  }

  public PayloadTooLargeException(ErrorCode errorCode, String customMessage) {
    super(errorCode, customMessage);
  }
}
