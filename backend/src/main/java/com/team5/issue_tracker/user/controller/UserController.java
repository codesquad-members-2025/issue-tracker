package com.team5.issue_tracker.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.user.dto.UserScrollResponse;
import com.team5.issue_tracker.user.query.UserQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserQueryService userQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<UserScrollResponse>> getScrolledUsers(
      @RequestParam(required = false) String cursor,
      @RequestParam(required = false, defaultValue = "10") Integer limit
  ) {
    log.info("GET /api/users 요청");
    return ResponseEntity.ok(ApiResponse.success(userQueryService.getScrolledUsers(cursor, limit)));
  }
}
