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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        log.info("[JWT Filter] Request URI: {}, Method: {}", requestURI, method);

        // ✅ CORS 헤더 설정
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://issue-tracker-fe-hosting.s3-website.ap-northeast-2.amazonaws.com");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // ✅ OPTIONS 요청은 인증 필요 없음
        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.info("[JWT Filter] OPTIONS 요청 우회됨");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // ✅ 인증 없이 통과시킬 경로
        if (
                requestURI.equals("/") ||
                        requestURI.equals("/index.html") ||
                        requestURI.equals("/favicon.ico") ||
                        requestURI.endsWith(".js") ||
                        requestURI.endsWith(".css") ||
                        requestURI.endsWith(".ico") ||
                        requestURI.endsWith(".svg") ||
                        requestURI.startsWith("/assets") ||
                        requestURI.startsWith("/static") ||
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
            BaseResponseDto responseDto = BaseResponseDto.failure("Authorization 헤더가 없거나 형식이 잘못되었습니다.");
            writeJsonResponse(httpResponse, responseDto, HttpStatus.UNAUTHORIZED);
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
            BaseResponseDto responseDto = BaseResponseDto.failure(e.getMessage());
            writeJsonResponse(httpResponse, responseDto, HttpStatus.UNAUTHORIZED);
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
