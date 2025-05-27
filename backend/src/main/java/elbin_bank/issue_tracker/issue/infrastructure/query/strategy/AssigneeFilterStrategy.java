package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class AssigneeFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(FilterCriteria c) {
        return !c.assignees().isEmpty();
    }

    @Override
    public void appendWhere(StringBuilder where,
                            MapSqlParameterSource p,
                            FilterCriteria c) {
        where.append(" AND asu.nickname IN (:assignees)");
        p.addValue("assignees", c.assignees());
    }

    @Override
    public void appendHaving(StringBuilder having,
                             MapSqlParameterSource p,
                             FilterCriteria c) {
        having.append("""
            AND COUNT(DISTINCT CASE
                   WHEN asu.nickname IN (:assignees) THEN asu.nickname END
               ) = :needAsg
        """);
    }

}
