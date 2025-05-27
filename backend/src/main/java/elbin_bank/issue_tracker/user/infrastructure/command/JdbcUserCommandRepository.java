package elbin_bank.issue_tracker.user.infrastructure.command;

import elbin_bank.issue_tracker.user.domain.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                    INSERT INTO assignee (issue_id, user_id)
                    SELECT :issueId, u.id
                      FROM `user` u
                     WHERE u.id IN (:assignees)
                """;

        var params = new MapSqlParameterSource()
                .addValue("issueId", issueId)
                .addValue("assignees", assignees);

        jdbc.update(sql, params);
    }

}
