package codesquad.team4.issuetracker.auth.oauth;

import codesquad.team4.issuetracker.exception.badrequest.InvalidOAuthStateException;
import codesquad.team4.issuetracker.auth.oauth.dto.OAuthRequestDto;
import codesquad.team4.issuetracker.auth.oauth.dto.OAuthResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Value("${github.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    private static final String GITHUB_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_API_URL = "https://api.github.com/user";


    public OAuthResponseDto.OAuthLoginUrl createGithubAuthorizeUrl(HttpSession session) {
        //state 생성 및 세션에 저장
        String state = UUID.randomUUID().toString();
        session.setAttribute("oauth_state", state);

        //Github 인증 URL 구성
        String githubUrl = UriComponentsBuilder.fromHttpUrl(GITHUB_AUTHORIZE_URL)
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("scope", "read:user user:email")
            .queryParam("state", state)
            .build()
            .toUriString();

        return OAuthResponseDto.OAuthLoginUrl.builder()
            .url(githubUrl)
            .build();
    }

    public void handleCallback(OAuthRequestDto.GitHubCallback callback, HttpSession session) {
        //state 값 비교
        String savedState = (String) session.getAttribute("oauth_state");
        if (!callback.getState().equals(savedState)) {
            throw new InvalidOAuthStateException();
        }

        //access tocken 요청
        String accessToken = requestAccessToken(callback.getCode());
        //Github 사용자 정보 요청
        OAuthResponseDto.GitHubUserResponse userInfo = fetchGitHubUser(accessToken);
        //(아직 구현 안함)사용자 DB 확인 -> 회원가입/로그인 처리
    }

    private String requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        //request
        Map<String, String> body = Map.of(
            "client_id", clientId,
            "client_secret", clientSecret,
            "code", code,
            "redirect_uri", redirectUri
        );

        //header, body를 합쳐서 하나의 요청으로 만듦
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        //Post 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(
            GITHUB_TOKEN_URL,
            request,
            Map.class
        );

        return (String) response.getBody().get("access_token");
    }

    private OAuthResponseDto.GitHubUserResponse fetchGitHubUser(String accessToken) {
        //Authorization 포함된 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        //Get 요청
        ResponseEntity<OAuthResponseDto.GitHubUserResponse> response = restTemplate.exchange(
            GITHUB_USER_API_URL,
            HttpMethod.GET,
            request,
            OAuthResponseDto.GitHubUserResponse.class
        );

        return response.getBody();
    }
}
