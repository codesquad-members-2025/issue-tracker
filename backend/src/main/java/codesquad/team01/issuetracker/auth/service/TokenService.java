package codesquad.team01.issuetracker.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.auth.domain.RefreshToken;
import codesquad.team01.issuetracker.auth.dto.AuthDto;
import codesquad.team01.issuetracker.auth.repository.RefreshTokenRepository;
import codesquad.team01.issuetracker.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final JwtUtil jwtUtil;
	private final RefreshTokenRepository refreshTokenRepository;

	public AuthDto.LoginResponse createTokens(int id, String profileImageUrl, String username) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("username", username);
		claims.put("profileImageUrl", profileImageUrl);

		//jwt 생성
		int subject = id;
		String accessToken = jwtUtil.createAccessToken(subject, claims);
		String refreshToken = jwtUtil.createRefreshToken(subject);

		RefreshToken token = RefreshToken.builder()
			.userId(subject)
			.token(refreshToken)
			.build();

		//refresh Token 저장
		refreshTokenRepository.save(token);

		return new AuthDto.LoginResponse(accessToken, refreshToken);
	}

}
