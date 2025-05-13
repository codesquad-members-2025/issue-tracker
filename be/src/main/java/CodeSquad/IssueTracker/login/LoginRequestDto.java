package CodeSquad.IssueTracker.login;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
@Data
public class LoginRequestDto {
    @NotBlank(message = "아이디를 입력하세요.")
    private final String loginId;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private final String password;
}
