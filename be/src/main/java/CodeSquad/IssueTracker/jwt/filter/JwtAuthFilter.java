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
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthFilter implements Filter {
    private final JWTUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // ✅ OPTIONS 요청은 CORS Preflight → 무조건 통과시켜야 함 (CorsFilter가 처리)
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String requestURI = httpRequest.getRequestURI();

        // 로그인/회원가입은 JWT 인증 제외
        if (requestURI.equals("/login") || requestURI.equals("/signup")) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        log.info("[JWT Filter] Request URI: {}", requestURI);
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            respondUnauthorized(httpResponse, "Authorization 헤더가 없거나 형식이 잘못되었습니다.");
            return;
        }

        String accessToken = authHeader.substring(7);
        try {
            Claims claims = jwtUtil.validateAccessToken(accessToken);
            httpRequest.setAttribute("loginId", claims.get("loginId"));
            log.info("[JWT Filter] loginId: {}", claims.get("loginId"));
        } catch (JwtValidationException e) {
            respondUnauthorized(httpResponse, e.getMessage());
            return;
        }

        filterChain.doFilter(httpRequest, httpResponse);
    }

    private void respondUnauthorized(HttpServletResponse response, String message) throws IOException {
        BaseResponseDto dto = BaseResponseDto.failure(message);
        String json = new ObjectMapper().writeValueAsString(dto);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }
}
