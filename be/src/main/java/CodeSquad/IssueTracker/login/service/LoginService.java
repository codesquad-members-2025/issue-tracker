package CodeSquad.IssueTracker.login.service;

import CodeSquad.IssueTracker.login.dto.LoginRequestDto;
import CodeSquad.IssueTracker.login.dto.LoginResponseDto;
import CodeSquad.IssueTracker.login.exception.PasswordMismatchException;
import CodeSquad.IssueTracker.login.exception.UserNotFoundException;
import CodeSquad.IssueTracker.user.repository.JdbcTemplatesUserRepository;
import CodeSquad.IssueTracker.user.JWTUtil;
import CodeSquad.IssueTracker.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoginService {

    private final JdbcTemplatesUserRepository userRepository;
    private final JWTUtil jwtUtil;

    /*
    사용자가 입력한 loginId, password와 대조하여 일치하면 Access Token과 Refresh Token을 반환하고 아니면 예외를 발생시킵니다. Refresh Token은 DB에도 저장합니다.
     */
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User targetUser = getUser(loginRequestDto.getLoginId());
        validatePassword(targetUser, loginRequestDto.getPassword());

        String accessToken = jwtUtil.createAccessToken(targetUser, "");
        String refreshToken = jwtUtil.createRefreshToken();

        return new LoginResponseDto(accessToken, refreshToken);
    }

    public User getUser(String loginId) {
        Optional<User> foundUser = userRepository.findUserByLoginId(loginId);
        return foundUser.orElseThrow(UserNotFoundException::new);
    }

    public void validatePassword(User targetUser, String password) {
        if (!targetUser.getPassword().equals(password)) {
            throw new PasswordMismatchException();
        }
    }
}
