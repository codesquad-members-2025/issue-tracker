package codesquad.team4.issuetracker.auth;

import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    public static final String USER_ATTRIBUTE = "authenticatedUser";
    private static final String NEED_LOGIN = "로그인이 필요합니다.";
    private static final String NOT_FOUND_USER = "사용자를 찾을 수 없습니다.";
    private static final String TOKEN_EXPIRED = "토큰이 만료되었습니다.";
    private static final String TOKEN_INVALID = "토큰이 유효하지않습니다.";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Optional<String> token = extractTokenFromCookies(request.getCookies());
        if (token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, NEED_LOGIN);
            return false;
        }

        TokenValidationResult result = jwtProvider.isValid(token.get());
        if (result == TokenValidationResult.EXPIRED) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, TOKEN_EXPIRED);
            return false;
        }

        if (result == TokenValidationResult.INVALID) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, TOKEN_INVALID);
            return false;
        }

        return authenticateRequest(request, response, token.get());
    }

    private Optional<String> extractTokenFromCookies(Cookie[] cookies) {
        if (cookies == null) return Optional.empty();

        for (Cookie cookie : cookies) {
            if ("access_token".equals(cookie.getName())) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }

    private boolean authenticateRequest(HttpServletRequest request, HttpServletResponse response, String token)
        throws IOException {
        Long userId = jwtProvider.getUserId(token);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, NOT_FOUND_USER);
            return false;
        }

        request.setAttribute(USER_ATTRIBUTE, optionalUser.get());
        return true;
    }
}
