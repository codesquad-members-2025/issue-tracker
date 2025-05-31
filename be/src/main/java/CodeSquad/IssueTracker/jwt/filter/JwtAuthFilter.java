package CodeSquad.IssueTracker.jwt.filter;

import CodeSquad.IssueTracker.global.dto.BaseResponseDto;
import CodeSquad.IssueTracker.jwt.exception.JwtValidationException;
import CodeSquad.IssueTracker.jwt.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter implements Filter {

    private final JWTUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        log.info("[JWT Filter] Request URI: {}, Method: {}", requestURI, method);

        // ✅ OPTIONS 요청은 인증 필요 없음
        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.info("[JWT Filter] OPTIONS 요청 우회됨");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // ✅ 인증 없이 통과시킬 경로 (정적 리소스 및 공개 API)
        if (
                requestURI.equals("/") ||
                        requestURI.equals("/index.html") ||
                        requestURI.matches(".*\\.(js|css|ico|svg|png|jpg|jpeg|woff2|ttf|html)$") ||
                        requestURI.startsWith("/static/") ||
                        requestURI.startsWith("/assets/") ||
                        requestURI.equals("/login") ||
                        requestURI.equals("/signup")
        ) {
            log.info("[JWT Filter] 인증 예외 경로 우회됨 ✅: {}", requestURI);
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        // ✅ Authorization 헤더 검증
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("[JWT Filter] Authorization 헤더 없음 또는 형식 오류");
            writeJsonResponse(httpResponse,
                    BaseResponseDto.failure("Authorization 헤더가 없거나 형식이 잘못되었습니다."),
                    HttpStatus.UNAUTHORIZED);
            return;
        }

        String accessToken = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateAccessToken(accessToken);
            httpRequest.setAttribute("id", claims.get("loginId"));
            httpRequest.setAttribute("loginId", claims.get("loginUser"));
            log.info("[JWT Filter] 유효한 토큰, 사용자 loginId: {}", claims.get("loginUser"));
        } catch (JwtValidationException e) {
            log.warn("[JWT Filter] JWT 검증 실패: {}", e.getMessage());
            writeJsonResponse(httpResponse, BaseResponseDto.failure(e.getMessage()), HttpStatus.UNAUTHORIZED);
            return;
        }

        // ✅ 통과
        filterChain.doFilter(httpRequest, httpResponse);
    }

    private void writeJsonResponse(HttpServletResponse response, BaseResponseDto dto, HttpStatus status) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        String json = new ObjectMapper().writeValueAsString(dto);
        response.getWriter().write(json);
    }
}
