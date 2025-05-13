package elbin_bank.issue_tracker.issue.infrastructure;

import lombok.Getter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.ArrayList;
import java.util.List;

public class IssueQueryBuilder {

    private final StringBuilder sql;
    @Getter
    private final MapSqlParameterSource params;
    private final List<String> where;

    public IssueQueryBuilder() {
        this.sql = new StringBuilder("SELECT DISTINCT i.* FROM Issue i");
        this.params = new MapSqlParameterSource();
        this.where = new ArrayList<>();
    }

    public IssueQueryBuilder filterClosed(Boolean isClosed) {
        if (isClosed != null) {
            where.add("i.is_closed = :isClosed");
            params.addValue("isClosed", isClosed);
        }

        return this;
    }

    public String buildSql() {
        if (!where.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", where));
        }

        return sql.toString();
    }

}
