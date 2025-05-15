package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("유저 필터링 정보 조회")
    void 유저_필터링_정보_조회() {
        // given
        String sql = "SELECT user_id, nickname, profile_image FROM user";
        List<UserDto.UserInfo> mockUsers = List.of(
                UserDto.UserInfo.builder()
                        .id(1L)
                        .nickname("user1")
                        .profileImage("image.com/a.jpg")
                        .build(),
                UserDto.UserInfo.builder()
                        .id(2L)
                        .nickname("user2")
                        .profileImage("image.com/b.jpg")
                        .build()
        );
        given(jdbcTemplate.query(eq(sql), any(RowMapper.class)))
                .willReturn(mockUsers);

        // when
        UserDto.UserFilter result = userService.getFilterUsers();

        // then
        assertThat(result.getUsers()).hasSize(2);
        assertThat(result.getUsers().get(0).getId()).isEqualTo(1L);
        assertThat(result.getUsers().get(0).getNickname()).isEqualTo("user1");
        assertThat(result.getUsers().get(0).getProfileImage()).isEqualTo("image.com/a.jpg");
    }
}

