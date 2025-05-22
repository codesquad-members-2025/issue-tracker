package codesquad.team01.issuetracker.auth.dto;

import jakarta.validation.Valid;
import lombok.Getter;

// null 검증, @valid
@Getter
public class LoginRequest {

	@Valid
	private String loginId;
	@Valid
	private String password;
}
