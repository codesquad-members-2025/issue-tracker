package codesquad.team4.issuetracker.oauth;

import codesquad.team4.issuetracker.oauth.dto.OAuthResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    private static final String GITHUB_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";

    public OAuthResponseDto.OAuthLoginUrlResponse createGithubAuthorizeUrl(HttpSession session) {
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

        return OAuthResponseDto.OAuthLoginUrlResponse.builder()
            .url(githubUrl)
            .build();
    }
}
