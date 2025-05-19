package codesquad.team01.issuetracker.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenExpMs = 1000 * 60 * 15;  // 15분
    private final long refreshTokenExpMs = 1000L * 60 * 60 * 24 * 7; //7일

    //엑세스 토큰 생성
    public String generateAccessToken(String loginId){
        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+accessTokenExpMs))
                .signWith(key)
                .compact();
    }

    //리프레시 토큰 생성
    public String generateRefreshToken(String loginId){
        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+refreshTokenExpMs))
                .signWith(key)
                .compact();
    }



}
