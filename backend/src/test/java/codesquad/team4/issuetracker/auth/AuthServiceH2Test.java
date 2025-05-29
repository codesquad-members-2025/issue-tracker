package codesquad.team4.issuetracker.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import codesquad.team4.issuetracker.auth.dto.AuthRequestDto.SignupRequestDto;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AuthServiceH2Test {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Test
    @DisplayName("비밀번호는 해싱된 후 DB에 저장되어야 한다")
    void passwordShouldBeHashed() {
        // given
        SignupRequestDto request = new SignupRequestDto("test@email.com", "nickname", "plainPassword");

        // when
        authService.createNewUser(request);
        User savedUser = userRepository.findByEmailOrNickname("test@email.com", "nickname").orElseThrow();

        // then
        assertThat(savedUser.getPassword()).isNotEqualTo("plainPassword");
        //무작위 솔트 값으로 인해 해싱마다 변경
        assertFalse(BCrypt.checkpw("plainPassword", savedUser.getPassword()));
    }

}
