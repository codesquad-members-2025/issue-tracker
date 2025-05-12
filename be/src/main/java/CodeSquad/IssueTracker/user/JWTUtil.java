package CodeSquad.IssueTracker.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JWTUtil {
    private final String accessSecretKey;
    private final String refreshSecretKey;
    private static final String CLAIM_USERNAME = "userName";
    private static final String CLAIM_IMG_URL = "imgUrl";

    public JWTUtil(
            @Value("${spring.jwt.access-key}") String accessSecretKey,
            @Value("${spring.jwt.refresh-key}") String refreshSecretKey
    ) {
        this.accessSecretKey = accessSecretKey;
        this.refreshSecretKey = refreshSecretKey;
    }

    private final long ACCESS_EXPIRATION_TIME = 3600000;
    private final long REFRESH_EXPIRATION_TIME = 86400000;

    /*
    JWT Access Token을 생성합니다.(페이로드에 유저 이름과 프로필 사진 링크 정보를 포함)
     */
    public String createAccessToken(User loginUser, String imgUrl) {
        return Jwts.builder()
                .subject(loginUser.getLoginId())
                .claim(CLAIM_USERNAME, loginUser.getUserName())
                .claim(CLAIM_IMG_URL, imgUrl)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(accessSecretKey.getBytes(UTF_8)))
                .compact();
    }

}
