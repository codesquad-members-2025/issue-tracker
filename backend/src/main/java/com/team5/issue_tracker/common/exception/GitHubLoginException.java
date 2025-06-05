package com.team5.issue_tracker.common.exception;

public class GitHubLoginException extends BaseException {
  public GitHubLoginException(ErrorCode errorCode) {
    super(errorCode);
  }

  public GitHubLoginException(ErrorCode errorCode, String customMessage) {
    super(errorCode, customMessage);
  }
}
