package codesquad.team01.issuetracker.auth.service;

import codesquad.team01.issuetracker.auth.client.GitHubClient;
import codesquad.team01.issuetracker.auth.dto.GitHubUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubClient gitHubClient;

    public GitHubUser loginWithGitHub(String code) {
        // 깃헙에 access token 요청
        String accessToken = gitHubClient.fetchAccessToken(code);

        // 사용자 정보 요청
        return gitHubClient.fetchUserInfo(accessToken);
    }
}
