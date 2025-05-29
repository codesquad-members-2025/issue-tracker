package codesquad.team4.issuetracker.auth.oauth;

import codesquad.team4.issuetracker.auth.oauth.dto.OAuthRequestDto;
import codesquad.team4.issuetracker.auth.oauth.dto.OAuthResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/login")
    public ResponseEntity<OAuthResponseDto.OAuthLoginUrl> githubLogin(HttpSession session) {
        OAuthResponseDto.OAuthLoginUrl response = oAuthService.createGithubAuthorizeUrl(session);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback( @RequestParam String code, @RequestParam String state, HttpSession session) {
        oAuthService.handleCallback(new OAuthRequestDto.GitHubCallback(code, state), session);
        return ResponseEntity.ok("인증성공"); //여기 바꿔줘야 함.... 홈화면?.., 몰라 그냥 상태만?
    }
}
