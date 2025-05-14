package CodeSquad.IssueTracker.login.controller;

import CodeSquad.IssueTracker.global.dto.DefaultDto;
import CodeSquad.IssueTracker.login.service.LoginService;
import CodeSquad.IssueTracker.login.dto.LoginRequestDto;
import CodeSquad.IssueTracker.login.dto.LoginResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<DefaultDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto response = loginService.login(loginRequestDto);
        return ResponseEntity.ok(new DefaultDto(true, "로그인을 성공했습니다.", response));
    }

}
