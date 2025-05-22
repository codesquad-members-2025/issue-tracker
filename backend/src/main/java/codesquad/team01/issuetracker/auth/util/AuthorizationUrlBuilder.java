package codesquad.team01.issuetracker.auth.util;

import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import codesquad.team01.issuetracker.common.config.GithubOAuthProperties;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorizationUrlBuilder {

	private final GithubOAuthProperties properties;

	// 인증을 위한 uri 조립 후 반환
	public URI buildAuthorizeUri(HttpSession session) {
		// 랜덤값으로 state 생성 후 세션에 넣음
		String state = UUID.randomUUID().toString();
		session.setAttribute("oauth_state", state);

		return UriComponentsBuilder
			.fromUriString(properties.getAuthorizeUri())
			.queryParam("client_id", properties.getClientId())
			.queryParam("redirect_uri", properties.getRedirectUri())
			.queryParam("scope", properties.getScope())
			.queryParam("state", state)
			.build()
			.toUri();
	}
}
