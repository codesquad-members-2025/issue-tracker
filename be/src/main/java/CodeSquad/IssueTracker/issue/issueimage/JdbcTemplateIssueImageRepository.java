package CodeSquad.IssueTracker.issue.issueimage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateIssueImageRepository implements IssueImageRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateIssueImageRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("issue_images")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public IssueImage save(IssueImage image) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(image);
        Number key = jdbcInsert.executeAndReturnKey(param);
        image.setId(key.longValue());
        return image;
    }

    @Override
    public List<IssueImage> findByIssueId(Long issueId) {
        String sql = "SELECT * FROM issue_images WHERE issue_id = :issueId";
        MapSqlParameterSource param = new MapSqlParameterSource("issueId", issueId);
        return template.query(sql, param, issueImageRowMapper());
    }

    private RowMapper<IssueImage> issueImageRowMapper() {
        return BeanPropertyRowMapper.newInstance(IssueImage.class);
    }
}
