package codesquad.team01.issuetracker.auth.service;

import codesquad.team01.issuetracker.auth.client.GitHubClient;
import codesquad.team01.issuetracker.auth.dto.GitHubUser;
import codesquad.team01.issuetracker.auth.dto.LoginResponse;
import codesquad.team01.issuetracker.user.repository.UserRepository;
import codesquad.team01.issuetracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubClient gitHubClient;
    private final UserRepository userRepository;

    private final String GITHUB = "github";

    public LoginResponse loginWithGitHub(String code) {
        // 깃헙에 access token 요청
        log.info("Starting GitHub login flow with code={}", code);
        String accessToken = gitHubClient.fetchAccessToken(code);

        // 사용자 정보 요청
        log.debug("Received access token");
        GitHubUser gitHubUser = gitHubClient.fetchUserInfo(accessToken);
        log.info("Fetched user info: id={}, githubId={}", gitHubUser.id(), gitHubUser.githubId());

        User oauthUser = findOrCreateUser(gitHubUser);
        log.info("Created LoginResponse for userId={}", oauthUser.getId());

        // todo : 임시조치. 지토가 넘겨받아서 수정할 예정
        return new LoginResponse(
                oauthUser.getId(),
                oauthUser.getEmail()
        );
    }

    private User findOrCreateUser(GitHubUser gitHubUser) {
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
}
