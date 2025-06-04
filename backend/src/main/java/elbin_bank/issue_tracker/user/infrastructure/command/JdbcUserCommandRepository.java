package elbin_bank.issue_tracker.user.infrastructure.command;

import elbin_bank.issue_tracker.user.domain.User;
import elbin_bank.issue_tracker.user.domain.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserCommandRepository implements UserCommandRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public void saveAssigneesToIssue(long issueId, List<Long> assignees) {
        if (assignees == null || assignees.isEmpty()) {
            return;
        }
        String sql = """
                INSERT IGNORE INTO assignee (issue_id, user_id)
                SELECT :issueId, u.id
                FROM `user` u
                WHERE u.id IN (:assignees)
                """;

        var params = new MapSqlParameterSource()
                .addValue("issueId", issueId)
                .addValue("assignees", assignees);

        jdbc.update(sql, params);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            return insert(user);
        } else {
            update(user);
            return user;
        }
    }

    private User insert(User user) {
        String sql = """
                INSERT INTO `user` (github_id, login, password, salt, nickname, profile_image_url, uuid)
                VALUES (:githubId, :login, :password, :salt, :nickname, :profileImageUrl, :uuid)
                """;

        var params = new MapSqlParameterSource()
                .addValue("githubId", user.getGithubId())
                .addValue("login", user.getLogin())
                .addValue("password", user.getPassword())
                .addValue("salt", user.getSalt())
                .addValue("nickname", user.getNickname())
                .addValue("profileImageUrl", user.getProfileImageUrl())
                .addValue("uuid", user.getUuid());

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(sql, params, kh, new String[]{"id"});
        long newId = kh.getKey().longValue();

        return new User(
                newId,
                user.getGithubId(),
                user.getLogin(),
                user.getPassword(),
                user.getSalt(),
                user.getNickname(),
                user.getProfileImageUrl(),
                user.getUuid()
        );
    }

    private void update(User user) {
        String sql = """
                UPDATE `user`
                   SET github_id = :githubId,
                       login = :login,
                       password = :password,
                       salt = :salt,
                       nickname = :nickname,
                       profile_image_url = :profileImageUrl,
                       uuid = :uuid
                 WHERE id = :id
                """;

        var params = new MapSqlParameterSource()
                .addValue("githubId", user.getGithubId())
                .addValue("login", user.getLogin())
                .addValue("password", user.getPassword())
                .addValue("salt", user.getSalt())
                .addValue("nickname", user.getNickname())
                .addValue("profileImageUrl", user.getProfileImageUrl())
                .addValue("uuid", user.getUuid())
                .addValue("id", user.getId());

        jdbc.update(sql, params);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = """
                SELECT id, 
                       github_id AS githubId,
                       login,
                       password,
                       salt,
                       nickname,
                       profile_image_url AS profileImageUrl,
                       uuid
                FROM `user`
                WHERE login = :login
                """;
        var params = new MapSqlParameterSource("login", login);

        return jdbc.query(sql, params, (rs, rowNum) ->
                new User(rs.getLong("id"),
                        rs.getObject("githubId", Long.class),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("salt"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl"),
                        rs.getString("uuid"))
        ).stream().findFirst();
    }

    @Override
    public Optional<User> findByOAuthId(long oauthId) {
        String sql = """
                SELECT id, 
                       github_id AS githubId,
                       login,
                       password,
                       salt,
                       nickname,
                       profile_image_url AS profileImageUrl,
                       uuid
                FROM `user`
                WHERE github_id = :oauthId
                """;
        var params = new MapSqlParameterSource("oauthId", oauthId);

        return jdbc.query(sql, params, (rs, rowNum) ->
                new User(rs.getLong("id"),
                        rs.getObject("githubId", Long.class),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("salt"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl"),
                        rs.getString("uuid"))
        ).stream().findFirst();
    }

    @Override
    public void deleteAssigneesFromIssue(long issueId, List<Long> assignees) {
        if (assignees == null || assignees.isEmpty()) {
            return;
        }

        String sql = """
                DELETE FROM assignee
                WHERE issue_id = :issueId
                AND user_id IN (:assignees)
                """;
        var params = new MapSqlParameterSource()
                .addValue("issueId", issueId)
                .addValue("assignees", assignees);
        jdbc.update(sql, params);
    }

}
