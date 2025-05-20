package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.user.dto.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public List<UserDto.UserInfo> findUserForFiltering() {
        String sql = "SELECT user_id, nickname, profile_image FROM `user`";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                UserDto.UserInfo.builder()
                        .id(rs.getLong("user_id"))
                        .nickname(rs.getString("nickname"))
                        .profileImage(rs.getString("profile_image"))
                        .build()
        );
    }
}
