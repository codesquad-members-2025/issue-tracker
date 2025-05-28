package codesquad.team4.issuetracker.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AuthRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class SignupRequestDto{
        @NotNull
        private String email;
        @NotNull
        private String password;
        @NotNull
        private String nickname;
    }
}
