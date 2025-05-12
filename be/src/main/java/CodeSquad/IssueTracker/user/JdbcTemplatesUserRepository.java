package CodeSquad.IssueTracker.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplatesUserRepository implements UserRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplatesUserRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        String sql = "SELECT * FROM users WHERE login_Id = :loginId";
        Map<String, Object> param = Map.of("loginId", loginId);
        User user = template.queryForObject(sql, param, userRowMapper());
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        Map<String, Object> param = Map.of("id", id);
        User user = template.queryForObject(sql, param, userRowMapper());
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user){
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        Number key = jdbcInsert.executeAndReturnKey(param);
        user.setId(key.longValue());
        return user;
    }

    private RowMapper<User> userRowMapper(){
        return BeanPropertyRowMapper.newInstance(User.class);
    }




}
