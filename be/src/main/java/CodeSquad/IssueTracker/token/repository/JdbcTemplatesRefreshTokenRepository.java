package CodeSquad.IssueTracker.token.repository;

import CodeSquad.IssueTracker.token.RefreshToken;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

@Repository
public class JdbcTemplatesRefreshTokenRepository implements RefreshTokenRepository {
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplatesRefreshTokenRepository(DataSource dataSource) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("refresh_tokens");
    }

    @Override
    public void save(RefreshToken refreshToken) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(refreshToken);
        jdbcInsert.execute(param);
    }
}
