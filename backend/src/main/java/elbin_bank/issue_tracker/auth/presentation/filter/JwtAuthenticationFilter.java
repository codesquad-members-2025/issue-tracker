package elbin_bank.issue_tracker.auth.presentation.filter;

import com.github.benmanes.caffeine.cache.Cache;
import elbin_bank.issue_tracker.auth.domain.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwt;
    private final Cache<String, Long> cache;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String path = req.getRequestURI();
        if (path.startsWith("/api/v1/auth/")) {
            chain.doFilter(req, res);
            return;
        }

        try {
            String token = resolveToken(req);

            if (token == null) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            Claims claims = jwt.validateJwt(token);
            String uuid = claims.getSubject();

            Long userId = cache.getIfPresent(uuid);
            // 캐시 미스 시 곧바로 401 Unauthorized 응답
            if (userId == null) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            req.setAttribute("user", userId);
            chain.doFilter(req, res);
        } catch (JwtException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT: " + e.getMessage());
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
