package CodeSquad.IssueTracker.jwt.util;

import CodeSquad.IssueTracker.jwt.exception.JwtValidationException;
import CodeSquad.IssueTracker.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;


@Component
public class JWTUtil {

    private final String accessSecretKey;
    private final String refreshSecretKey;

    private static final String CLAIM_LOGIN_ID = "loginId";
    private static final String CLAIM_IMG_URL = "imgUrl";

    public JWTUtil(org.springframework.core.env.Environment env) {
        this.accessSecretKey = env.getProperty("spring.jwt.access-key");
        this.refreshSecretKey = env.getProperty("spring.jwt.refresh-key");
    }

    public static final long ACCESS_EXPIRATION_TIME = 86400000;
    public static final long REFRESH_EXPIRATION_TIME = 2592000000L;

    public String createAccessToken(User loginUser) {
        return Jwts.builder()
                .setSubject(loginUser.getLoginId())
                .claim(CLAIM_LOGIN_ID, loginUser.getLoginId())
                .claim(CLAIM_IMG_URL, loginUser.getProfileImageUrl())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Claims validateAccessToken(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new JwtValidationException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtValidationException("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtValidationException("JWT 토큰이 잘못되었습니다.");
        }
    }
}

