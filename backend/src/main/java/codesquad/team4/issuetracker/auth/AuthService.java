package codesquad.team4.issuetracker.auth;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.EMAIL_ALREADY_EXIST;
import static codesquad.team4.issuetracker.exception.ExceptionMessage.NICKNAME_ALREADY_EXIST;

import codesquad.team4.issuetracker.auth.dto.AuthRequestDto.LoginRequestDto;
import codesquad.team4.issuetracker.auth.dto.AuthRequestDto.SignupRequestDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.exception.ExceptionMessage;
import codesquad.team4.issuetracker.exception.badrequest.InvalidLoginTypeException;
import codesquad.team4.issuetracker.exception.conflict.EmailAlreadyExistException;
import codesquad.team4.issuetracker.exception.conflict.NicknameAlreadyExistException;
import codesquad.team4.issuetracker.exception.unauthorized.PasswordNotEqualException;
import codesquad.team4.issuetracker.exception.unauthorized.UserByEmailNotFoundException;
import codesquad.team4.issuetracker.user.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${profile.default-image-url}")
    private String DEFAULT_PROFILE_URL;

    private final UserRepository userRepository;
    public void createNewUser(SignupRequestDto request) {
        //이메일, 닉네임 중복 확인
        validateDuplicateEmailOrNickname(request);

        //패스워드 해싱
        String hashPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        //DB에 저장
        User user = User.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .password(hashPassword)
            .profileImage(DEFAULT_PROFILE_URL)
            .loginType(LoginType.LOCAL)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        userRepository.save(user);
    }

    private void validateDuplicateEmailOrNickname(SignupRequestDto request) {
        userRepository.findByEmailOrNickname(request.getEmail(), request.getNickname())
                .ifPresent(user -> {
                    if (user.getEmail().equals(request.getEmail())) {
                        throw new EmailAlreadyExistException(EMAIL_ALREADY_EXIST);
                    }
                    if (user.getNickname().equals(request.getNickname())) {
                        throw new NicknameAlreadyExistException(NICKNAME_ALREADY_EXIST);
                    }
                });
    }

    public User authenticateUser(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(UserByEmailNotFoundException::new);

        if (user.getLoginType() != LoginType.LOCAL) {
            throw new InvalidLoginTypeException(ExceptionMessage.INVALID_LOGINTYPE_LOCAL);
        }

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new PasswordNotEqualException();
        }

        return user;
    }
}
