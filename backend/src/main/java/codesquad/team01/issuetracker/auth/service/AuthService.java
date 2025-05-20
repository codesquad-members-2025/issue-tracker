package codesquad.team01.issuetracker.auth.service;

import codesquad.team01.issuetracker.auth.client.GitHubClient;
import codesquad.team01.issuetracker.auth.dto.GitHubUser;
import codesquad.team01.issuetracker.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubClient gitHubClient;

    public LoginResponse loginWithGitHub(String code) {
        // 깃헙에 access token 요청
        log.info("Starting GitHub login flow with code={}", code);
        String accessToken = gitHubClient.fetchAccessToken(code);

        // 사용자 정보 요청
        log.debug("Received access token");
        GitHubUser gitHubUser = gitHubClient.fetchUserInfo(accessToken);

        log.info("Fetched user info: id={}, githubId={}", gitHubUser.id(), gitHubUser.githubId());
        LoginResponse loginResponse = new LoginResponse(
                gitHubUser.githubId(),
                gitHubUser.avatarUrl(),
                gitHubUser.email()
        );
        log.info("Created LoginResponse for userId={}", loginResponse.id());
        return loginResponse;
    }
}
