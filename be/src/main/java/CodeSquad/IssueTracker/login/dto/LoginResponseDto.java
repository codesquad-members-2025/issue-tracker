package CodeSquad.IssueTracker.login.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private final String accessToken;
    private final String refreshToken;
}
