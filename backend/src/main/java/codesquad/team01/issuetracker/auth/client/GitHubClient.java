package codesquad.team01.issuetracker.auth.client;

import codesquad.team01.issuetracker.auth.dto.GitHubEmail;
import codesquad.team01.issuetracker.auth.dto.GitHubUser;
import codesquad.team01.issuetracker.common.config.GithubOAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
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
		log.info("Requesting access token from GitHub for code={}", code);
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
		String token = (String)tokenResponse.getBody().get(ACCESS_TOKEN);
		log.info("GitHub access token fetched");
		return token;
	}

	// 사용자 정보 요청
	public GitHubUser fetchUserInfo(String accessToken) {
		log.info("Fetching GitHub user info with accessToken");
		HttpHeaders authHeaders = new HttpHeaders();
		authHeaders.setBearerAuth((accessToken));
		HttpEntity<Void> userRequest = new HttpEntity<>(authHeaders);

		ResponseEntity<Map> userResponse = restTemplate.exchange(
			properties.getUserInfoUri(), HttpMethod.GET, userRequest, Map.class
		);

		Map<String, Object> userMap = userResponse.getBody();
		log.debug("GitHub user info response: {}", userMap);
		Long id = ((Number)userMap.get(ID)).longValue();
		String githubId = (String)userMap.get(LOGIN);
		String avatarUrl = (String)userMap.get(AVATAR_URL);

		String email = (String)userMap.get("email");
		if (email == null) {
			var emails = fetchUserEmail(accessToken);
			email = emails.stream()
				.filter(GitHubEmail::primary)
				.filter(GitHubEmail::verified)
				.findFirst()
				.map(GitHubEmail::email)
				.orElse(null);
		}

		log.info("GitHubUser parsed: id={}, login={}", id, githubId);
		return new GitHubUser(id, githubId, avatarUrl, email);
	}

	// 사용자가 깃헙에서 이메일을 공개하지 않았을 경우, 리소스 서버에 이메일 추가 요청
	public List<GitHubEmail> fetchUserEmail(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<GitHubEmail[]> response = restTemplate.exchange(
			properties.getUserEmailUri(),
			HttpMethod.GET,
			request,
			GitHubEmail[].class
		);
		return List.of(response.getBody());
	}
}
