package elbin_bank.issue_tracker.issue.infrastructure.query;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.FilterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SqlBuilder {

    private final List<FilterStrategy> strategies;

    public SqlClauseResult build(FilterCriteria crit) {
        StringBuilder join = new StringBuilder();
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        StringBuilder having = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        for (FilterStrategy s : strategies) {
            if (s.supports(crit)) {
                s.applyJoin(join, crit);
                s.applyWhere(where, params, crit);
                s.applyHaving(having, params, crit);
            }
        }

        return SqlClauseResult.of(join.toString(), where, having, params);
    }

}
