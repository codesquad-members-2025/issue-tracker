package codesquad.team01.issuetracker.auth.api;

import codesquad.team01.issuetracker.auth.dto.GitHubUser;
import codesquad.team01.issuetracker.auth.dto.LoginResponse;
import codesquad.team01.issuetracker.auth.service.AuthService;
import codesquad.team01.issuetracker.auth.util.AuthorizationUrlBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthorizationUrlBuilder authorizationUrlBuilder;

    // Authorization endpoint
    @GetMapping("/api/v1/oauth/github/login")
    public void redirectToGithub(HttpServletResponse response, HttpSession session) throws IOException {
        URI githubUri = authorizationUrlBuilder.buildAuthorizeUri(session);
        response.sendRedirect(githubUri.toString());
    }

    // Redirect(Callback) endpoint
    @GetMapping("/api/v1/oauth/callback")
    public LoginResponse githubCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpSession session) {
        String savedState = (String) session.getAttribute("oauth_state");
        if (savedState == null || !savedState.equals(state)) {
            throw new IllegalStateException("유효하지 않은 state");
        }
        session.removeAttribute("oauth_state");

        return authService.loginWithGitHub(code);
    }
}
