package CodeSquad.IssueTracker.comment.commnetimage;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateCommentImageRepository implements CommentImageRepository{


    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateCommentImageRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("comment_images")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public CommentImage save(CommentImage commentImage) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(commentImage);
        Number key = jdbcInsert.executeAndReturnKey(param);
        commentImage.setId(key.longValue());
        return commentImage;
    }

    @Override
    public void deleteByCommentId(Long issueId) {
        String sql = "DELETE FROM comment_images WHERE issue_id = :issueId";
        MapSqlParameterSource param = new MapSqlParameterSource("issueId", issueId);
        template.update(sql, param);
    }

    @Override
    public CommentImage findByCommentId(Long issueId) {
        String sql = "SELECT * FROM comment_images WHERE issue_id = :issueId";
        MapSqlParameterSource param = new MapSqlParameterSource("issueId", issueId);
        return template.queryForObject(sql, param, commentImageRowMapper());
    }

    private RowMapper<CommentImage> commentImageRowMapper() {
        return BeanPropertyRowMapper.newInstance(CommentImage.class);
    }
}
