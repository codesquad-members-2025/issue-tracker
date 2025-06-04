package com.team5.issue_tracker.common.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team5.issue_tracker.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/github")
public class GitHubAuthController {
  private final GitHubOAuthService gitHubOAuthService;

  @GetMapping("/callback")
  public ResponseEntity<ApiResponse<LoginResponse>> githubCallback(@RequestParam String code) {
    LoginResponse loginResponse = gitHubOAuthService.loginWithGithub(code);
    return ResponseEntity.ok(ApiResponse.success(loginResponse));
  }
}
