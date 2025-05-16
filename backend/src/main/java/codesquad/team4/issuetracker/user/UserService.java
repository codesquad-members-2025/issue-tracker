package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.user.dto.UserDto;
import codesquad.team4.issuetracker.user.dto.UserDto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public UserDto.UserFilter getFilterUsers() {
        List<UserInfo> users = userDao.findUserForFiltering();

        return UserDto.UserFilter.builder()
                .users(users)
                .build();
    }
}
