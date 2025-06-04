package com.team5.issue_tracker.common.auth;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.GitHubLoginException;
import com.team5.issue_tracker.user.domain.User;
import com.team5.issue_tracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitHubOAuthService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Value("${oauth.github.client-id}")
  private String clientId;

  @Value("${oauth.github.client-secret}")
  private String clientSecret;

  @Value("${oauth.github.redirect-uri}")
  private String redirectUri;

  public LoginResponse loginWithGithub(String code) {
    // 1. GitHub에서 access_token 요청
    String accessToken = getAccessToken(code);

    // 2. access_token으로 GitHub 사용자 정보 요청
    GitHubUser githubUser = getUserInfo(accessToken);

    // 3. 우리 시스템에 GitHub username이 이미 있으면 가져오고, 없으면 저장
    User user = userRepository.findByUsername(githubUser.getLogin())
        .orElseGet(() -> userRepository.save(User.oauthSignup(githubUser)));

    // 4. JWT 발급
    String jwt = jwtTokenProvider.createToken(user.getId(), user.getUsername());
    return new LoginResponse(jwt);
  }

  private String getAccessToken(String code) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    Map<String, String> body = Map.of(
        "client_id", clientId,
        "client_secret", clientSecret,
        "code", code,
        "redirect_uri", redirectUri
    );

    HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
    ResponseEntity<Map> response = restTemplate.postForEntity(
        "https://github.com/login/oauth/access_token", request, Map.class
    );

    if (response.getBody() == null || !response.getBody().containsKey("access_token")) {
      throw new GitHubLoginException(ErrorCode.INVALID_GITHUB_CODE);
    }

    return (String) response.getBody().get("access_token");
  }

  private GitHubUser getUserInfo(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<GitHubUser> response = restTemplate.exchange(
        "https://api.github.com/user", HttpMethod.GET, entity, GitHubUser.class
    );

    return response.getBody();
  }
}
