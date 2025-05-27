package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class MilestoneFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(FilterCriteria c) {
        return !c.milestones().isEmpty();
    }

    @Override
    public void appendWhere(StringBuilder where,
                            MapSqlParameterSource p,
                            FilterCriteria c) {
        where.append(" AND m.title IN (:milestones)");
        p.addValue("milestones", c.milestones());
    }

}
