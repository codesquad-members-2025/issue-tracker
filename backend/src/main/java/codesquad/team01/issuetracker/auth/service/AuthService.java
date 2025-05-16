package codesquad.team01.issuetracker.auth.service;

import codesquad.team01.issuetracker.auth.domain.User;
import codesquad.team01.issuetracker.auth.dto.LoginResponse;
import codesquad.team01.issuetracker.auth.repository.UserRepository;
import codesquad.team01.issuetracker.common.config.GithubOAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final GithubOAuthProperties properties;
    private final RestTemplate restTemplate;

    public LoginResponse loginWithGitHub(String code) {
        // access token 요청
        Map<String, String> tokenRequest = Map.of(
                "client_id", properties.getClientId(),
                "client_secret", properties.getClientSecret(),
                "code", code,
                "redirect_uri", properties.getRedirectUri()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(tokenRequest, headers);

        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                properties.getTokenUri(), request, Map.class
        );
        String accessToken = (String) tokenResponse.getBody().get("access_token");

        // 사용자 정보 요청
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth((accessToken));
        HttpEntity<Void> userRequest = new HttpEntity<>(authHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(   // ?
                properties.getUserInfoUri(), HttpMethod.GET, userRequest, Map.class
        );
        Map<String, Object> userMap = userResponse.getBody();
        Long githubId = ((Number) userMap.get("id")).longValue();   // ?
        String loginId = (String) userMap.get("login");
        String avatarUrl = (String) userMap.get("avatar_url");

        // 사용자 저장 혹은 업데이트
        User user = userRepository.findByLoginId(loginId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .loginId(loginId)
                                .providerId(String.valueOf(githubId))
                                .authProvider("github")
                                .username(loginId)
                                .email(null)
                                .build()
                        )
                );

        // 로그인 응답 생성
        return new LoginResponse(
                user.getId(),
                user.getLoginId(),
                avatarUrl
        );
    }
}
