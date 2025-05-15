package CodeSquad.IssueTracker.milestone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplatesMilestoneRepository implements MilestoneRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplatesMilestoneRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("milestones")
                .usingGeneratedKeyColumns("milestone_id");
    }

    @Override
    public Milestone save(Milestone milestone) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(milestone);
        Number key = jdbcInsert.executeAndReturnKey(param);
        milestone.setMilestoneId(key.longValue());
        return milestone;
    }

    @Override
    public Optional<Milestone> findById(Long milestoneId) {
        String sql = "SELECT * FROM milestones WHERE milestone_id = :milestoneId";
        Map<String, Object> param = Map.of("milestoneId", milestoneId);
        Milestone milestone = template.queryForObject(sql, param, milestoneRowMapper());
        return Optional.ofNullable(milestone);
    }

    @Override
    public List<Milestone> findAll() {
        String sql = "SELECT * FROM milestones";
        return template.query(sql, milestoneRowMapper());
    }

    @Override
    public void deleteById(Long milestoneId) {
        String sql = "DELETE FROM milestones WHERE milestone_id = :milestoneId";
        Map<String, Object> param = Map.of("milestoneId", milestoneId);
        template.update(sql, param);
    }

    @Override
    public void update(Long id, MilestoneUpdateDto updateDto) {
        String sql = """
            UPDATE milestones 
            SET name = :name,
                description = :description,
                end_date = :endDate,        
                is_open = :isOpen           
            WHERE milestone_id = :id       
            """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", updateDto.getName())
                .addValue("description", updateDto.getDescription())
                .addValue("endDate", updateDto.getEndDate())
                .addValue("isOpen", updateDto.getIsOpen())
                .addValue("id", id);
        template.update(sql, param);
    }

    private RowMapper<Milestone> milestoneRowMapper() {
        return BeanPropertyRowMapper.newInstance(Milestone.class);
    }
}
