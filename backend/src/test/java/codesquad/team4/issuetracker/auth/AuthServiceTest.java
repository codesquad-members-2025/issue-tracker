package codesquad.team4.issuetracker.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import codesquad.team4.issuetracker.auth.dto.AuthRequestDto.SignupRequestDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.exception.conflict.EmailAlreadyExistException;
import codesquad.team4.issuetracker.exception.conflict.NicknameAlreadyExistException;
import codesquad.team4.issuetracker.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;


    @Test
    @DisplayName("DB에 같은 이메일이 존재하면 예외가 발생한다")
    void emailDuplicateSignupTest() {
        SignupRequestDto request = new SignupRequestDto("duplicate@example.com", "password", "nickname");

        User existingUser = User.builder()
            .id(1L)
            .email("duplicate@example.com")
            .nickname("otherNickname")
            .build();

        given(userRepository.findByEmailOrNickname("duplicate@example.com", "nickname"))
            .willReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> authService.createNewUser(request))
            .isInstanceOf(EmailAlreadyExistException.class);
    }

    @Test
    @DisplayName("DB에 같은 닉네임이 존재하면 예외가 발생한다")
    void nicknameDuplicateSignupTest() {
        SignupRequestDto request = new SignupRequestDto("test@example.com", "password", "duplicatedNickname");

        User existingUser = User.builder()
            .id(1L)
            .email("other@example.com")
            .nickname("duplicatedNickname")
            .build();

        given(userRepository.findByEmailOrNickname("test@example.com", "duplicatedNickname"))
            .willReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> authService.createNewUser(request))
            .isInstanceOf(NicknameAlreadyExistException.class);
    }
}
