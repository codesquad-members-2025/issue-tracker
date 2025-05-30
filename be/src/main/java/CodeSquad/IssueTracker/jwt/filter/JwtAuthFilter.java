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

        // ✅ 모든 요청에 대해 CORS 헤더 추가
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://issue-tracker-fe-hosting.s3-website.ap-northeast-2.amazonaws.com");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // ✅ OPTIONS 요청은 여기서 처리 끝
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            log.info("[JWT Filter] OPTIONS 요청 우회됨");
            httpResponse.setHeader("Access-Control-Allow-Origin", "http://issue-tracker-fe-hosting.s3-website.ap-northeast-2.amazonaws.com");
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return; // ❗ 이게 반드시 있어야 인증 필터 우회됨
        }

        String requestURI = httpRequest.getRequestURI();

        // ✅ 인증 예외 경로
        if (requestURI.equals("/login") || requestURI.equals("/signup")) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        log.info("[JWT Filter] Request URI: {}", requestURI);
        String authHeader = httpRequest.getHeader("Authorization");

        // ✅ Authorization 헤더가 없거나 잘못된 경우
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            BaseResponseDto responseDto = BaseResponseDto.failure("Authorization 헤더가 없거나 형식이 잘못되었습니다.");
            writeJsonResponse(httpResponse, responseDto, HttpStatus.UNAUTHORIZED);
            return;
        }

        String accessToken = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateAccessToken(accessToken);
            httpRequest.setAttribute("id", claims.get("loginId"));      // 사용자 식별자
            httpRequest.setAttribute("loginId", claims.get("loginUser")); // 사용자 로그인 ID
            log.info("[JWT Filter] loginId: {}", claims.get("loginUser"));
        } catch (JwtValidationException e) {
            BaseResponseDto responseDto = BaseResponseDto.failure(e.getMessage());
            writeJsonResponse(httpResponse, responseDto, HttpStatus.UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(httpRequest, httpResponse);
    }

    private void writeJsonResponse(HttpServletResponse response, BaseResponseDto dto, HttpStatus status) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        String json = new ObjectMapper().writeValueAsString(dto);
        response.getWriter().write(json);
    }
}
