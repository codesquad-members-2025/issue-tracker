package elbin_bank.issue_tracker.auth.presentation.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(
        @NotBlank(message = "아이디를 반드시 입력해야 합니다.")
        @Size(min = 4, message = "아이디 최소 4자 이상이어야 합니다.")
        String login,

        @NotBlank(message = "비밀번호를 반드시 입력해야 합니다.")
        @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
        String password,

        @NotBlank(message = "이름을 반드시 입력해야 합니다.")
        @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하여야 합니다.")
        String nickname,

        String profileImageUrl
) {
}
