package codesquad.team01.issuetracker.auth.api;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.team01.issuetracker.auth.dto.AuthDto;
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
		// CSRF 방지용 state 생성 및 세션 저장
		String state = UUID.randomUUID().toString();
		session.setAttribute("oauth_state", state);

		// URL 생성
		URI githubUri = authorizationUrlBuilder.buildAuthorizeUri(state);

		response.sendRedirect(githubUri.toString());
	}

	// Redirect(Callback) endpoint
	@GetMapping("/api/v1/oauth/callback")
	public AuthDto.LoginResponse githubCallback(
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
	public AuthDto.LoginResponse login(@RequestBody @Valid AuthDto.LoginRequest request) {
		return authService.login(request.loginId(), request.password());
	}
}
