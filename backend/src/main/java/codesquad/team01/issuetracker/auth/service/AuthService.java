package codesquad.team01.issuetracker.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import codesquad.team01.issuetracker.auth.domain.User;
import codesquad.team01.issuetracker.auth.dto.LoginResponse;
import codesquad.team01.issuetracker.auth.dto.LoginResponseDto;
import codesquad.team01.issuetracker.auth.repository.UserRepository;
import codesquad.team01.issuetracker.common.config.GithubOAuthProperties;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final GithubOAuthProperties properties;
	private final RestTemplate restTemplate;

	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;
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
		Map<String, String> tokenRequest = Map.of(CLIENT_ID, properties.getClientId(), CLIENT_SECRET,
			properties.getClientSecret(), AUTHORIZATION_CODE, code, REDIRECT_URI, properties.getRedirectUri());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		HttpEntity<Map<String, String>> request = new HttpEntity<>(tokenRequest, headers);

		ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(properties.getTokenUri(), request, Map.class);
		String accessToken = (String)tokenResponse.getBody().get(ACCESS_TOKEN);

		// 사용자 정보 요청
		HttpHeaders authHeaders = new HttpHeaders();
		authHeaders.setBearerAuth((accessToken));
		HttpEntity<Void> userRequest = new HttpEntity<>(authHeaders);

		ResponseEntity<Map> userResponse = restTemplate.exchange(properties.getUserInfoUri(), HttpMethod.GET,
			userRequest, Map.class);
		Map<String, Object> userMap = userResponse.getBody();
		Long githubId = ((Number)userMap.get(ID)).longValue();
		String loginId = (String)userMap.get(LOGIN);
		String avatarUrl = (String)userMap.get(AVATAR_URL);

		// 사용자 저장(회원가입) 혹은 업데이트
		User user = userRepository.findByLoginId(loginId)
			.orElseGet(() -> userRepository.save(User.builder()
				.loginId(loginId)
				.providerId(githubId)
				.authProvider(GITHUB)
				.username(loginId)
				.email(null)
				.build()));

		// 로그인 응답 생성
		return new LoginResponse(Long.parseLong(String.valueOf(user.getId())), user.getLoginId(), avatarUrl);
	}

	public LoginResponseDto login(String loginId, String password) {
		//user조회 -> payloadDto
		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		LoginResponseDto response = getPayloadDto(user.getId(), user.getProfileImageUrl(), user.getUsername());

		return response;
	}

	private LoginResponseDto getPayloadDto(Integer id, String profileImageUrl, String username) {
		LoginResponseDto dto = tokenService.createTokens(id, profileImageUrl, username);
		return dto;
	}

}




