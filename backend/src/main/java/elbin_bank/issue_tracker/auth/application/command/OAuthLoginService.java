package elbin_bank.issue_tracker.auth.application.command;

import com.github.benmanes.caffeine.cache.Cache;
import elbin_bank.issue_tracker.auth.application.command.dto.TokenResponseDto;
import elbin_bank.issue_tracker.auth.domain.JwtProvider;
import elbin_bank.issue_tracker.auth.infrastructure.provider.GithubOAuthProvider;
import elbin_bank.issue_tracker.auth.infrastructure.provider.dto.OAuthUserInfoDto;
import elbin_bank.issue_tracker.user.domain.User;
import elbin_bank.issue_tracker.user.domain.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final JwtProvider jwt;
    private final UserCommandRepository userCommandRepository;
    private final GithubOAuthProvider githubOAuthProvider;
    private final Cache<String, Long> cache;

    public String getAuthorizationUrl(String state) {
        return githubOAuthProvider.buildAuthorizationUrl(state);
    }

    @Transactional
    public TokenResponseDto handleGithubLogin(String code) {
        // 1) code → AccessToken 교환
        String accessToken = githubOAuthProvider.getAccessToken(code);

        // 2) AccessToken → GitHub 사용자 정보 조회
        OAuthUserInfoDto userInfo = githubOAuthProvider.getUserInfo(accessToken);

        // 3) DB 조회
        Optional<User> user = userCommandRepository.findByOAuthId(userInfo.providerId());

        // 4) 사용자 정보가 존재하면 로그인 처리, 없으면 회원가입 후 로그인 처리
        if (user.isPresent()) {
            return doLogin(user.get());
        }

        String uuid = UUID.randomUUID().toString();
        User newUser = User.createByOauth(
                userInfo.providerId(),
                userInfo.nickname(),
                userInfo.avatarUrl(),
                uuid
        );

        User savedUser =userCommandRepository.save(newUser);

        return doLogin(savedUser);
    }

    private TokenResponseDto doLogin(User user) {
        cache.put(user.getUuid(), user.getId());
        return jwt.createJwt(user);
    }

}
