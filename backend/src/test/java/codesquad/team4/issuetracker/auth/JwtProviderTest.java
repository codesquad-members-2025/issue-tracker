package codesquad.team4.issuetracker.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtProviderTest {

    private JwtProvider jwtProvider;

    private final String secret = "testsecrettestsecrettestsecrettestsecret"; // 32 bytes 이상

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider();
        ReflectionTestUtils.setField(jwtProvider, "secret", secret);
    }

    @Test
    @DisplayName("JWT 토큰이 생성되면 userId를 파싱할 수 있다")
    void createAndParseToken() {
        //given
        Long userId = 123L;

        //when
        String token = jwtProvider.createToken(userId);
        Long parsedUserId = jwtProvider.getUserId(token);

        //then
        assertThat(token).isNotBlank();
        assertThat(parsedUserId).isEqualTo(userId);
    }

    @Test
    @DisplayName("유효한 토큰이면 isValid가 true를 반환한다")
    void isValid_validToken() {
        //when
        String token = jwtProvider.createToken(1L);
        boolean isValid = jwtProvider.isValid(token);

        //then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("잘못된 토큰이면 isValid가 false를 반환한다")
    void isValid_invalidToken() {
        //when
        String invalidToken = "InvalidToken";
        boolean isValid = jwtProvider.isValid(invalidToken);

        //then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰이면 isValid가 false를 반환한다")
    void isValid_expiredToken(){

        Date now = new Date();
        Date expiredAt = new Date(now.getTime() - 1000); // 이미 만료된 시점

        String expiredToken = Jwts.builder()
            .setSubject("1")
            .setIssuedAt(now)
            .setExpiration(expiredAt) // 만료된 시간
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
            .compact();

        boolean isValid = jwtProvider.isValid(expiredToken);
        assertThat(isValid).isFalse();
    }
}
