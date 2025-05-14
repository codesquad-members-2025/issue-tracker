package CodeSquad.IssueTracker.login.controller;

import CodeSquad.IssueTracker.global.dto.BaseResponseDto;
import CodeSquad.IssueTracker.login.service.LoginService;
import CodeSquad.IssueTracker.login.dto.LoginRequestDto;
import CodeSquad.IssueTracker.login.dto.LoginResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public BaseResponseDto<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto response = loginService.login(loginRequestDto);
        return BaseResponseDto.success("로그인을 성공했습니다.", response);
    }

}
