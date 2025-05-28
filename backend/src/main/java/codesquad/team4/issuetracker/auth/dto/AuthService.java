package codesquad.team4.issuetracker.auth.dto;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.EMAIL_ALREADY_EXIST;
import static codesquad.team4.issuetracker.exception.ExceptionMessage.NICKNAME_ALREADY_EXIST;

import codesquad.team4.issuetracker.auth.dto.AuthRequestDto.SignupRequestDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.exception.conflict.EmailAlreadyExistException;
import codesquad.team4.issuetracker.exception.conflict.NicknameAlreadyExistException;
import codesquad.team4.issuetracker.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

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
            .createdAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now())
            .build();

        userRepository.save(user);
    }

    private void validateDuplicateEmailOrNickname(SignupRequestDto request) {
        Optional<User> duplicate = userRepository.findByEmailOrNickname(request.getEmail(), request.getNickname());

        if (duplicate.isPresent()) {
            User user = duplicate.get();
            if (user.getEmail().equals(request.getEmail())) {
                throw new EmailAlreadyExistException(EMAIL_ALREADY_EXIST);
            }
            if (user.getNickname().equals(request.getNickname())) {
                throw new NicknameAlreadyExistException(NICKNAME_ALREADY_EXIST);
            }
        }
    }
}
