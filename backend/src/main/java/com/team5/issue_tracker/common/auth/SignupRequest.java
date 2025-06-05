package com.team5.issue_tracker.common.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
  @NotBlank(message = "사용자 이름을 입력해야 합니다.")
  private String username;

  @Email(message = "유효한 이메일 주소를 입력해야 합니다.")
  private String email;

  private String imageUrl;

  @NotBlank(message = "비밀번호를 입력해야 합니다.")
  private String password;
}
