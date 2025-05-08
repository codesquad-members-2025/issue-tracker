package CodeSquad.IssueTracker.issue;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateIssueRepository implements IssueRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateIssueRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("issues")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Issue save(Issue issue) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(issue);
        Number key = jdbcInsert.executeAndReturnKey(param);
        issue.setId(key.longValue());
        return issue;
    }

    @Override
    public void update(Long issueId, IssueUpdateDto updateParam) {
        String sql = "UPDATE issues SET title = :title, isOpen = :isOpen, timestamp = :timestamp, assigneeId = :assigneeId, milestoneId = :milestoneId WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title",updateParam.getTitle())
                .addValue("isOpen",updateParam.getIsOpen())
                .addValue("timestamp",updateParam.getTimestamp())
                .addValue("mildstondId",updateParam.getMilestoneId())
                .addValue("assigneeId",updateParam.getAssigneeId())
                .addValue("id",issueId);
        template.update(sql, param);
    }

    @Override
    public Optional<Issue> findById(Long issueId) {
        String sql = "SELECT * FROM issues WHERE id = :id";
        Map<String, Object> param = Map.of("id", issueId);
        Issue issue = template.queryForObject(sql, param, issueRowMapper());
        return Optional.ofNullable(issue);
    }

    @Override
    public List<Issue> findAll() {
        String sql = "SELECT * FROM issues";
        return template.query(sql, issueRowMapper());
    }


    private RowMapper<Issue> issueRowMapper(){
        return BeanPropertyRowMapper.newInstance(Issue.class);
    }
}
