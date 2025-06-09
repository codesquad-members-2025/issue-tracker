package com.team5.issue_tracker.common.util;

import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {

  public static Long extractUserId(HttpServletRequest request) {
    Object userId = request.getAttribute("jwt.userId");
    if (userId == null) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
    }
    return (Long) userId;
  }
}
