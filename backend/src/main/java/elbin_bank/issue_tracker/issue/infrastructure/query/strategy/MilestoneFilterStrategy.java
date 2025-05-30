package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class MilestoneFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(FilterCriteria c) {
        return c.milestone() != null;
    }

    @Override
    public void applyJoin(StringBuilder join, FilterCriteria c) {
        // no-op
    }

    @Override
    public void applyWhere(StringBuilder where, MapSqlParameterSource params, FilterCriteria c) {
        where.append(" AND m.title = :milestone");
        params.addValue("milestone", c.milestone());
    }

    @Override
    public void applyHaving(StringBuilder having, MapSqlParameterSource params, FilterCriteria c) {
        // no-op
    }

}
