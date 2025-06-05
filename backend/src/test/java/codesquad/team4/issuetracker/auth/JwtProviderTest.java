package codesquad.team4.issuetracker.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
public class JwtProviderTest {

    @Autowired
    private JwtProvider  jwtProvider;

    private final String secret = "testsecrettestsecrettestsecrettestsecret"; // 32 bytes 이상

    @BeforeEach
    void setUp() {
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
    @DisplayName("유효한 토큰이면 result가 VALID를 반환한다")
    void isValid_validToken() {
        //when
        String token = jwtProvider.createToken(1L);
        TokenValidationResult result = jwtProvider.isValid(token);

        //then
        assertThat(result).isEqualTo(TokenValidationResult.VALID);
    }

    @Test
    @DisplayName("잘못된 토큰이면 result가 INVALID를 반환한다")
    void isValid_invalidToken() {
        //when
        String invalidToken = "InvalidToken";
        TokenValidationResult result = jwtProvider.isValid(invalidToken);

        //then
        assertThat(result).isEqualTo(TokenValidationResult.INVALID);
    }

    @Test
    @DisplayName("만료된 토큰이면 result가 EXPIRED를 반환한다")
    void isValid_expiredToken() {
        //give
        String expiredToken = jwtProvider.createToken(1L, -1000L);

        //when
        TokenValidationResult result = jwtProvider.isValid(expiredToken);

        //then
        assertThat(result).isEqualTo(TokenValidationResult.EXPIRED);
    }
}
