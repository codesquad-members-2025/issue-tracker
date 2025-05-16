package CodeSquad.IssueTracker.issueAssignee;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateAssigneeRepository implements IssueAssigneeRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateAssigneeRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("issue_assignee")
                .usingGeneratedKeyColumns("issue_assignee_id");
    }

    @Override
    public void save(IssueAssignee issueAssignee) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(issueAssignee);
        Number key = jdbcInsert.executeAndReturnKey(param);
        issueAssignee.setIssueAssigneeId(key.longValue());
    }

    @Override
    public void deleteByIssueId(Long issueId) {
        String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }


}
