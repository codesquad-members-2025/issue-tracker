package codesquad.team01.issuetracker.auth.client;

import codesquad.team01.issuetracker.auth.dto.GitHubUser;
import codesquad.team01.issuetracker.common.config.GithubOAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final RestTemplate restTemplate;
    private final GithubOAuthProperties properties;

    private final String CLIENT_ID = "client_id";
    private final String CLIENT_SECRET = "client_secret";
    private final String AUTHORIZATION_CODE = "code";
    private final String REDIRECT_URI = "redirect_uri";
    private final String ID = "id";
    private final String LOGIN = "login";
    private final String AVATAR_URL = "avatar_url";
    private final String GITHUB = "github";
    private final String ACCESS_TOKEN = "access_token";

    // token 요청
    public String fetchAccessToken(String code) {
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

        return (String) tokenResponse.getBody().get(ACCESS_TOKEN);
    }

    // 사용자 정보 요청
    public GitHubUser fetchUserInfo(String accessToken) {
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth((accessToken));
        HttpEntity<Void> userRequest = new HttpEntity<>(authHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                properties.getUserInfoUri(), HttpMethod.GET, userRequest, Map.class
        );

        Map<String, Object> userMap = userResponse.getBody();
        Long id = ((Number) userMap.get(ID)).longValue();
        String githubId = (String) userMap.get(LOGIN);
        String avatarUrl = (String) userMap.get(AVATAR_URL);

        return new GitHubUser(id, githubId, avatarUrl);
    }
}
