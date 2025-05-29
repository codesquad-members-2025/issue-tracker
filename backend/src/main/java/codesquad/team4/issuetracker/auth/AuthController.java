package codesquad.team4.issuetracker.auth;

import codesquad.team4.issuetracker.auth.dto.AuthRequestDto;
import codesquad.team4.issuetracker.auth.dto.AuthResponseDto;
import codesquad.team4.issuetracker.auth.dto.AuthResponseDto.LoginResponseDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid AuthRequestDto.SignupRequestDto request) {
        authService.createNewUser(request);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponseDto.LoginResponseDto> login(
        @RequestBody @Valid AuthRequestDto.LoginRequestDto requestDto,  HttpServletResponse response) {

        User user = authService.checkEmailAndPassword(requestDto);

        String token = jwtProvider.createToken(user.getId());

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
            .httpOnly(true)
            .path("/")
            .maxAge(60 * 60 * 24) // 1일
            .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.success(createLoginUser(user));
    }

    private LoginResponseDto createLoginUser(User user) {
        return LoginResponseDto.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .profileImage(user.getProfileImage())
            .build();
    }
}
