package codesquad.team4.issuetracker.auth.oauth;

import codesquad.team4.issuetracker.auth.JwtProvider;
import codesquad.team4.issuetracker.auth.dto.AuthResponseDto;
import codesquad.team4.issuetracker.auth.oauth.dto.OAuthRequestDto;
import codesquad.team4.issuetracker.auth.oauth.dto.OAuthResponseDto;
import codesquad.team4.issuetracker.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    private final JwtProvider jwtProvider;

    @GetMapping("/login")
    public ResponseEntity<OAuthResponseDto.OAuthLoginUrl> githubLogin(HttpSession session) {
        OAuthResponseDto.OAuthLoginUrl githubUrl = oAuthService.createGithubAuthorizeUrl(session);
        return ResponseEntity.ok(githubUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthResponseDto.LoginResponseDto> callback(
        @RequestParam String code, @RequestParam String state,
        HttpSession session, HttpServletResponse response) {

        User user = processOAuthCallback(code, state, session);
        setJwtCookie(response, user);
        return respondWithLoginUser(user);
    }

    //GitHub 인증 처리 + 로그인/회원가입
    private User processOAuthCallback(String code, String state, HttpSession session) {
        return oAuthService.handleCallback(new OAuthRequestDto.GitHubCallback(code, state), session);
    }

    //JWT 발급 및 쿠키 설정
    private void setJwtCookie(HttpServletResponse response, User user) {
        String token = jwtProvider.createToken(user.getId());

        ResponseCookie cookie = ResponseCookie.from("access_token", token)
            .httpOnly(true)
            .sameSite("Strict")
            .path("/")
            .maxAge(60 * 60 * 24)
            .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    //사용자 정보 응답
    private ResponseEntity<AuthResponseDto.LoginResponseDto> respondWithLoginUser(User user) {
        AuthResponseDto.LoginResponseDto loginResponse = oAuthService.loginUser(user);
        return ResponseEntity.ok(loginResponse);
    }
}
