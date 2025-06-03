package codesquad.team4.issuetracker.auth.dto;

import codesquad.team4.issuetracker.auth.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AuthResponseDto {
    @AllArgsConstructor
    @Getter
    @Builder
    public static class LoginResponseDto{
        private Long userId;
        private String nickname;
        private String profileImage;
    }
}
