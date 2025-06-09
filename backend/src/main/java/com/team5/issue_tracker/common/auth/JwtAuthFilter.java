package com.team5.issue_tracker.common.auth;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.issue_tracker.common.dto.ApiError;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private static final ObjectMapper mapper =
      new ObjectMapper().setSerializationInclusion(JsonInclude.Include.ALWAYS); // ✅ null 포함 명시

  public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();
    log.info("요청 path = {}", path);

    List<String> excludedPaths = List.of("/api/auth", "/api/swagger-ui", "/api/v3/api-docs");
    if (excludedPaths.stream().anyMatch(path::startsWith)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = resolveToken(request);

    if (token == null) {
      log.warn("Unauthorized 요청: [{} {}] - 토큰 없음", request.getMethod(), path);
      sendJsonErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
          "로그인이 필요한 서비스입니다.");
      return;
    }

    try {
      Claims claims = jwtTokenProvider.parseClaims(token);
      String email = claims.getSubject();
      Long userId = claims.get("userId", Long.class);

      request.setAttribute("jwt.email", email);
      request.setAttribute("jwt.userId", userId);
    } catch (JwtException e) {
      log.warn("Invalid JWT token: {}", e.getMessage());
      sendJsonErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
          "유효하지 않은 토큰입니다.");
      return;
    } catch (Exception e) {
      log.warn(e.getMessage());
      sendJsonErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
          "알 수 없는 오류가 발생했습니다.");
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    if (bearer != null && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }
    return null;
  }

  private void sendJsonErrorResponse(HttpServletResponse response, int status,
      String message) throws IOException {
    response.setStatus(status);
    response.setContentType("application/json; charset=UTF-8");

    Map<String, Object> responseBody = new LinkedHashMap<>();
    responseBody.put("success", false);
    responseBody.put("data", null);
    responseBody.put("error", new ApiError(message, status));


    String json = mapper.writeValueAsString(responseBody);
    response.getWriter().write(json);
  }
}
