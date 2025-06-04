package elbin_bank.issue_tracker.auth.infrastructure.provider;

import elbin_bank.issue_tracker.auth.infrastructure.provider.dto.OAuthUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GithubOAuthProvider {

    @Value("${github.client-id}")
    private String clientId;

    @Value("${github.client-secret}")
    private String clientSecret;

    @Value("${github.redirect-uri}")
    private String redirectUri;

    @Value("${github.scopes}")
    private String scopes;

    private final RestTemplate restTemplate;

    private static final String AUTHORIZE_BASE_URL = "https://github.com/login/oauth/authorize";
    private static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_URL = "https://api.github.com/user";

    /**
     * GitHub 인가(Authorization) URL 생성
     */
    public String buildAuthorizationUrl(String state) {
        String encRedirect = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String encScope = URLEncoder.encode(scopes, StandardCharsets.UTF_8);

        return AUTHORIZE_BASE_URL +
                "?client_id=" + clientId +
                "&redirect_uri=" + encRedirect +
                "&scope=" + encScope +
                "&state=" + state;
    }

    /**
     * code → accessToken 교환
     * */
    public String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code,
                "redirect_uri", redirectUri
        );
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp = restTemplate.postForEntity(TOKEN_URL, entity, Map.class);
        if (resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null) {
            throw new IllegalStateException("GitHub access_token 요청 실패");
        }
        return (String) resp.getBody().get("access_token");
    }

    /**
     * accessToken → GitHub 사용자 정보 조회
     * */
    public OAuthUserInfoDto getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> userResp = restTemplate.exchange(USER_URL, HttpMethod.GET, entity, Map.class);
        if (userResp.getStatusCode() != HttpStatus.OK || userResp.getBody() == null) {
            throw new IllegalStateException("GitHub 기본 사용자 정보 조회 실패");
        }
        Map userMap = userResp.getBody();

        // Jackson이 Integer로 변환하는 경우가 있어 Number로 처리
        Number numberId = (Number) userMap.get("id");
        Long providerId = numberId.longValue();

        String nickname = (String) userMap.get("login");
        String avatarUrl = (String) userMap.get("avatar_url");

        return new OAuthUserInfoDto("github", providerId, nickname, avatarUrl);
    }

}
