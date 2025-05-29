package codesquad.team4.issuetracker.oauth;

import codesquad.team4.issuetracker.oauth.dto.OAuthResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/login")
    public ResponseEntity<String> githubLogin(HttpSession session) {
        OAuthResponseDto.OAuthLoginUrlResponse response = oAuthService.createGithubAuthorizeUrl(session);
        return ResponseEntity.ok(response);
    }
}
