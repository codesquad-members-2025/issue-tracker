package CodeSquad.IssueTracker.label;

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
import java.util.Map;

@Repository
public class JdbcTemplatesLabelRepository implements LabelRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplatesLabelRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("labels")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Label save(Label label) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(label);
        Number key = jdbcInsert.executeAndReturnKey(param);
        label.setLabelId(key.longValue());
        return label;
    }

    @Override
    public List<Label> findAll() {
        String sql = "SELECT * FROM labels";
        return template.query(sql, labelRowMapper());
    }

    @Override
    public List<Label> findByIssueId(Long issueId) {
        String sql = """
                SELECT l.* FROM labels l
                JOIN issue_labels il ON l.id = il.label_id
                WHERE il.issue_id = :issueId
                """;
        return template.query(sql, Map.of("issueId", issueId), labelRowMapper());
    }

    @Override
    public void update(Long labelId, LabelUpdateDto updateDto) {
        String sql = "UPDATE labels SET name = :name, description = :description, color = :color, createdAt = :createdAt WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", updateDto.getName())
                .addValue("description", updateDto.getDescription())
                .addValue("color", updateDto.getColor())
                .addValue("createdAt", updateDto.getCreatedAt())
                .addValue("id", labelId);
        template.update(sql, param);
    }

    @Override
    public void deleteById(Long labelId) {
        String sql = "DELETE FROM labels WHERE id = :id";
        Map<String, Object> param = Map.of("id", labelId);
        template.update(sql, param);
    }

    private RowMapper<Label> labelRowMapper(){
        return BeanPropertyRowMapper.newInstance(Label.class);
    }
}
