package elbin_bank.issue_tracker.issue.infrastructure.query;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.FilterStrategy;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlBuilder {

    private final List<FilterStrategy> strategies;

    public SqlBuilder(List<FilterStrategy> strategies) {
        this.strategies = strategies;
    }

    public SqlClauseResult buildSql(FilterCriteria crit) {
        StringBuilder where  = new StringBuilder();
        StringBuilder having = new StringBuilder();
        MapSqlParameterSource params = new MapSqlParameterSource();

        for (FilterStrategy st : strategies) {
            if (!st.supports(crit)) continue;

            st.appendWhere(where, params, crit);
            st.appendHaving(having, params, crit);
        }

        return new SqlClauseResult(where.toString(), having.toString(), params);
    }

}
