package CodeSquad.IssueTracker.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplatesUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatesUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        String sql = "SELECT * FROM users WHERE login_id = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{loginId}, new UserRowMapper());
        return users.stream().findFirst();
    }

    public class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getLong("id"))
                    .loginId(rs.getString("login_id"))
                    .password(rs.getString("password"))
                    .emailId(rs.getString("email"))
                    .nickName(rs.getString("user_name"))
                    .build();
        }
    }
}
