package CodeSquad.IssueTracker.issueLabel;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class JdbcTemplateIssueLabelRepository implements IssueLabelRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateIssueLabelRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("issue_labels")
                .usingGeneratedKeyColumns("issue_label_id");
    }

    @Override
    public void save(IssueLabel issueLabel) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(issueLabel);
        Number key = jdbcInsert.executeAndReturnKey(param);
        issueLabel.setIssueLabelId(key.longValue());
    }

    @Override
    public void deleteByIssueId(Long issueId) {
        String sql = "DELETE FROM issue_labels WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }
}
