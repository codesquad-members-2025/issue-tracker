package com.team5.issue_tracker.common.auth;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.GitHubLoginException;
import com.team5.issue_tracker.user.domain.User;
import com.team5.issue_tracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
    log.info("Access token: {}", accessToken);

    // 2. access_token으로 GitHub 사용자 정보 요청
    GitHubUser githubUser = getUserInfo(accessToken);
    log.info("Github user email: {}", githubUser.getEmail());
    log.info("Github user login: {}", githubUser.getLogin());
    log.info("Github user avatar_url: {}", githubUser.getAvatar_url());

    // 3. 우리 시스템에 GitHub useremail이 이미 있으면 가져오고, 없으면 저장
    if (githubUser.getEmail() == null){
      String email = fetchPrimaryEmail(accessToken);
      githubUser.setEmail(email);
    }
    log.info("Github user email: {}", githubUser.getEmail());

    User user = userRepository.findByEmail(githubUser.getEmail())
        .orElseGet(() -> userRepository.save(User.oauthSignup(githubUser)));


    log.info("User: {}", user);
    // 4. JWT 발급
    String jwt = jwtTokenProvider.createToken(user.getId(), user.getEmail());
    return new LoginResponse(jwt);
  }

  private String getAccessToken(String code) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    MultiValueMap<String, String> body =  new LinkedMultiValueMap<>();
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);
    body.add("code", code);
    body.add("redirect_uri", redirectUri);

    log.info("Client_ID: {}", clientId);
    log.info("Client_Secret: {}", clientSecret);
    log.info("Code:  {}", code);
    log.info("Redirect_URI: {}", redirectUri);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
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

  public String fetchPrimaryEmail(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    String url = "https://api.github.com/user/emails";
    ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        entity,
        new ParameterizedTypeReference<>() {}
    );

    return response.getBody().stream()
        .filter(email -> Boolean.TRUE.equals(email.get("primary")) && Boolean.TRUE.equals(email.get("verified")))
        .map(email -> (String) email.get("email"))
        .findFirst()
        .orElseThrow(() -> new GitHubLoginException(ErrorCode.INVALID_GITHUB_CODE));
  }
}
