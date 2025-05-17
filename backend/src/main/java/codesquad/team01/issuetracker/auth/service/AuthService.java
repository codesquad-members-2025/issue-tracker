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

    public final String CLIENT_ID = "client_id";
    public final String CLIENT_SECRET = "client_secret";
    public final String AUTHORIZATION_CODE = "code";
    public final String REDIRECT_URI = "redirect_uri";
    public final String ID = "id";
    public final String LOGIN = "login";
    public final String AVATAR_URL = "avatar_url";
    public final String GITHUB = "github";
    public final String ACCESS_TOKEN = "access_token";

    // 깃헙에서 받은 authorization code 받은 후 access token 요청하고 사용자 정보 요청
    public LoginResponse loginWithGitHub(String code) {
        // 깃헙에 access token 요청
        Map<String, String> tokenRequest = Map.of(
                CLIENT_ID, properties.getClientId(),
                CLIENT_SECRET, properties.getClientSecret(),
                AUTHORIZATION_CODE, code,
                REDIRECT_URI, properties.getRedirectUri()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(tokenRequest, headers);

        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                properties.getTokenUri(), request, Map.class
        );
        String accessToken = (String) tokenResponse.getBody().get(ACCESS_TOKEN);

        // 사용자 정보 요청
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth((accessToken));
        HttpEntity<Void> userRequest = new HttpEntity<>(authHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                properties.getUserInfoUri(), HttpMethod.GET, userRequest, Map.class
        );
        Map<String, Object> userMap = userResponse.getBody();
        Long githubId = ((Number) userMap.get(ID)).longValue();
        String loginId = (String) userMap.get(LOGIN);
        String avatarUrl = (String) userMap.get(AVATAR_URL);

        // 사용자 저장(회원가입) 혹은 업데이트
        User user = userRepository.findByLoginId(loginId)
                .orElseGet(() -> userRepository.save(
                                User.builder()
                                        .loginId(loginId)
                                        .providerId(String.valueOf(githubId))
                                        .authProvider(GITHUB)
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
