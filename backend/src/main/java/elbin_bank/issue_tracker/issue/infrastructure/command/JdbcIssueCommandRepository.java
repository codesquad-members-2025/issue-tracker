package elbin_bank.issue_tracker.issue.infrastructure.command;

import elbin_bank.issue_tracker.issue.application.command.dto.IssueCreateCommand;
import elbin_bank.issue_tracker.issue.domain.IssueCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcIssueCommandRepository implements IssueCommandRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Long create(IssueCreateCommand cmd) {
        String sqlIssue = """
            INSERT INTO issue (author_id, milestone_id, title, contents, is_closed)
             VALUES (:authorId, :milestone, :title, :content, FALSE)
        """;
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(sqlIssue,
                new MapSqlParameterSource()
                        .addValue("authorId", cmd.authorId())
                        .addValue("milestone", cmd.milestone())
                        .addValue("title", cmd.title())
                        .addValue("content", cmd.content()),
                kh,
                new String[]{"id"});
        Long issueId = kh.getKey().longValue();

        if (cmd.assignees() != null && !cmd.assignees().isEmpty()) {
            String sqlAss = "INSERT INTO assignee (user_id, issue_id) VALUES (:uid, :iid)";
            for (Long uid : cmd.assignees()) {
                jdbc.update(sqlAss,
                        new MapSqlParameterSource()
                                .addValue("uid", uid)
                                .addValue("iid", issueId));
            }
        }

        if (cmd.labels() != null && !cmd.labels().isEmpty()) {
            String sqlLbl = "INSERT INTO issue_label (issue_id, label_id) VALUES (:iid, :lid)";
            for (Long lid : cmd.labels()) {
                jdbc.update(sqlLbl,
                        new MapSqlParameterSource()
                                .addValue("iid", issueId)
                                .addValue("lid", lid));
            }
        }

        return issueId;
    }

}
