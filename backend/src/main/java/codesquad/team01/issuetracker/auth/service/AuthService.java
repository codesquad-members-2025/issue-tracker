package codesquad.team01.issuetracker.auth.service;

import codesquad.team01.issuetracker.auth.client.GitHubClient;
import codesquad.team01.issuetracker.auth.dto.GitHubUser;
import codesquad.team01.issuetracker.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubClient gitHubClient;

    public LoginResponse loginWithGitHub(String code) {
        // 깃헙에 access token 요청
        String accessToken = gitHubClient.fetchAccessToken(code);

        // 사용자 정보 요청
        GitHubUser gitHubUser = gitHubClient.fetchUserInfo(accessToken);

        LoginResponse loginResponse = new LoginResponse(
                gitHubUser.githubId(),
                gitHubUser.avatarUrl(),
                gitHubUser.email()
        );
        return loginResponse;
    }
}
