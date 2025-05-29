package codesquad.team4.issuetracker.user;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.team4.issuetracker.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJdbcTest
@ActiveProfiles("test")
@Import({UserDao.class, UserService.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("""
            INSERT INTO `user` (user_id, email, nickname, profile_image, created_at, updated_at, password)
            VALUES
                (1, 'user1@test.com', 'user1', 'image.com/a.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'password'),
                (2, 'user2@test.com', 'user2', 'image.com/b.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'password')
        """);
    }

    @Test
    @DisplayName("유저 필터링 정보 조회")
    void 유저_필터링_정보_조회() {
        UserDto.UserFilter result = userService.getFilterUsers();

        assertThat(result.getUsers()).hasSize(2);
        assertThat(result.getUsers().get(0).getId()).isEqualTo(1L);
        assertThat(result.getUsers().get(0).getNickname()).isEqualTo("user1");
        assertThat(result.getUsers().get(0).getProfileImage()).isEqualTo("image.com/a.jpg");
    }
}
