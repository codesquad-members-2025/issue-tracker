package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.user.dto.UserFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JdbcTemplate jdbcTemplate;

    public UserFilterDto getFilterUsers() {
        String sql = "SELECT user_id, nickname, profile_image FROM user";

        List<IssueResponseDto.UserInfo> users = jdbcTemplate.query(sql, (rs, rowNum) ->
                IssueResponseDto.UserInfo.builder()
                        .id(rs.getLong("user_id"))
                        .nickname(rs.getString("nickname"))
                        .profileImage(rs.getString("profile_image"))
                        .build()
        );
        return new UserFilterDto(users);
    }
}
