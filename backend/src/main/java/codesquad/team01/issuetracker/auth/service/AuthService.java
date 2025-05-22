package codesquad.team01.issuetracker.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import codesquad.team01.issuetracker.auth.client.GitHubClient;
import codesquad.team01.issuetracker.auth.dto.AuthDto;
import codesquad.team01.issuetracker.auth.dto.LoginResponseDto;
import codesquad.team01.issuetracker.common.config.GithubOAuthProperties;
import codesquad.team01.issuetracker.user.domain.User;
import codesquad.team01.issuetracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final GitHubClient gitHubClient;
	private final UserRepository userRepository;
	private final GithubOAuthProperties properties;
	private final RestTemplate restTemplate;

	private final String GITHUB = "github";
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;
	public final String CLIENT_ID = "client_id";
	public final String CLIENT_SECRET = "client_secret";
	public final String AUTHORIZATION_CODE = "code";
	public final String REDIRECT_URI = "redirect_uri";
	public final String ID = "id";
	public final String LOGIN = "login";
	public final String AVATAR_URL = "avatar_url";
	public final String ACCESS_TOKEN = "access_token";

	public AuthDto.LoginResponse loginWithGitHub(String code) {
		// 깃헙에 access token 요청
		log.info("Starting GitHub login flow with code={}", code);
		String accessToken = gitHubClient.fetchAccessToken(code);

		// 사용자 정보 요청
		log.debug("Received access token");
		AuthDto.GitHubUser gitHubUser = gitHubClient.fetchUserInfo(accessToken);
		log.info("Fetched user info: id={}, githubId={}", gitHubUser.id(), gitHubUser.githubId());

		User oauthUser = findOrCreateUser(gitHubUser);
		log.info("Created LoginResponse for userId={}", oauthUser.getId());

		// todo : 임시조치. 지토가 넘겨받아서 수정할 예정
		return new AuthDto.LoginResponse(
			oauthUser.getId(),
			oauthUser.getEmail()
		);
	}

	private User findOrCreateUser(AuthDto.GitHubUser gitHubUser) {
		return userRepository
			.findByProviderIdAndAuthProvider(gitHubUser.id(), GITHUB)
			.orElseGet(() -> userRepository.save(
				User.builder()
					.loginId(null)
					.username(gitHubUser.githubId())
					.email(gitHubUser.email())
					.providerId(gitHubUser.id())
					.authProvider(GITHUB)
					.build()
			));
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
