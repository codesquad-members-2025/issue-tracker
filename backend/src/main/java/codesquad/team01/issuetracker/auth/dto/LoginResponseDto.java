package codesquad.team01.issuetracker.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {

	//login_id -> id로 변경, expiresIn 제거
	private String accessToken;
	private String refreshToken;

}
