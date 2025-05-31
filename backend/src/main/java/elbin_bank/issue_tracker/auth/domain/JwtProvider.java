package elbin_bank.issue_tracker.auth.domain;

import elbin_bank.issue_tracker.auth.application.command.dto.TokenResponseDto;
import elbin_bank.issue_tracker.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public TokenResponseDto createJwt(User user) {
        long millis = System.currentTimeMillis();
        Date now = new Date(millis);
        Date expiration = new Date(millis + expirationTime);

        String jwt = Jwts.builder()
                .subject(user.getUuid()) // JWT의 Subject로 사용자 UUID 설정
                .claims(userToMap(user)) // 토큰에 담을 Claim 정보
                .issuedAt(now) // 토큰 생성 시간
                .expiration(expiration) // 토큰 만료 시간
                .signWith(getKey(), Jwts.SIG.HS256) // 서명 알고리즘과 키 설정
                .compact(); // JWT 생성

        return new TokenResponseDto(jwt, "Bearer");
    }

    /**
     * JWT를 검증하고 Claims를 반환합니다.
     *
     * @param jwt 검증할 JWT 문자열
     * @return 검증된 Claims 객체
     * @throws io.jsonwebtoken.JwtException JWT 검증 실패 시 발생
     */
    public Claims validateJwt(String jwt) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("nickname", user.getNickname());
        userMap.put("profileImageUrl", user.getProfileImageUrl());

        return userMap;
    }

}
