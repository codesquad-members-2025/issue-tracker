package codesquad.team01.issuetracker.auth.api;

import codesquad.team01.issuetracker.auth.util.AuthorizationUrlBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthorizationUrlBuilder authorizationUrlBuilder;

    @GetMapping("/api/v1/oauth/github/login")
    public void redirectToGithub(HttpServletResponse response, HttpSession session) throws IOException {
        URI githubUri = authorizationUrlBuilder.buildAuthorizeUri(session);
        response.sendRedirect(githubUri.toString());
    }
}
