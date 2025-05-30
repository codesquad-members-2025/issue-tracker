package com.team5.issue_tracker.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
  private final ErrorCode errorCode;
  private final String customMessage;

  public BaseException(ErrorCode errorCode) {
    super();
    this.customMessage = errorCode.getMessage();
    this.errorCode = errorCode;
  }

  public BaseException(ErrorCode errorCode, String customMessage) {
    super();
    this.customMessage = customMessage;
    this.errorCode = errorCode;
  }
}
