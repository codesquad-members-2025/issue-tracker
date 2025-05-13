package CodeSquad.IssueTracker.login;

import lombok.Data;

@Data
public class LoginResponseDto {
    private final String accessToken;
    private final String refreshToken;
}
