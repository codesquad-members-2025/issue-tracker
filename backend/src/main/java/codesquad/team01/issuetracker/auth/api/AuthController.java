package codesquad.team01.issuetracker.auth.api;

import java.io.IOException;
import java.net.URI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.team01.issuetracker.auth.dto.LoginRequest;
import codesquad.team01.issuetracker.auth.dto.LoginResponse;
import codesquad.team01.issuetracker.auth.dto.LoginResponseDto;
import codesquad.team01.issuetracker.auth.service.AuthService;
import codesquad.team01.issuetracker.auth.util.AuthorizationUrlBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
		String savedState = (String)session.getAttribute("oauth_state");
		if (savedState == null || !savedState.equals(state)) {
			throw new IllegalStateException("유효하지 않은 state");
		}
		session.removeAttribute("oauth_state");

		return authService.loginWithGitHub(code);
	}

	//자체 로그인
	//에러 발생 시 API Response 적용 예정
	@PostMapping("/api/v1/auth/login")
	public LoginResponseDto login(@RequestBody @Valid LoginRequest request) {
		return authService.login(request.getLoginId(), request.getPassword());
	}
}