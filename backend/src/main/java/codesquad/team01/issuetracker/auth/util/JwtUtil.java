package codesquad.team01.issuetracker.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	private final SecretKey key;

	@Value("${jwt.access-token-validity}")
	private final long accessTokenValidity;

	@Value("${jwt.refresh-token-validity}")
	private final long refreshTokenValidity;

	public JwtUtil(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.access-token-validity}") long accessTokenValidity,
		@Value("${jwt.refresh-token-validity}") long refreshTokenValidity
	) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.accessTokenValidity = accessTokenValidity;
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String createAccessToken(int subject, Map<String, Object> claims) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + accessTokenValidity);
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(String.valueOf(subject))
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken(int subject) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + refreshTokenValidity);
		return Jwts.builder()
			.setSubject(String.valueOf(subject))
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (JwtException e) {
			throw new IllegalArgumentException("허용되지 않거나 만료된 토큰입니다");
		}
	}

	public long getAccessTokenExpiresInSeconds() {
		return accessTokenValidity / 1000;
	}

}
