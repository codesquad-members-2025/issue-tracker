package com.team5.issue_tracker.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.user.dto.UserPageResponse;
import com.team5.issue_tracker.user.query.UserQueryService;
import com.team5.issue_tracker.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final UserQueryService userQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<UserPageResponse>> getAllUsers() {
    log.info("GET /api/users 요청");
    return ResponseEntity.ok(ApiResponse.success(userQueryService.getAllUsers()));
  }
}
