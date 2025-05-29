package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AssigneeFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(FilterCriteria c) {
        return !c.assignees().isEmpty();
    }

    @Override
    public void applyJoin(StringBuilder join, FilterCriteria c) {
        join.append(" JOIN assignee ass ON ass.issue_id = i.id")
                .append(" JOIN `user` u2 ON u2.id = ass.user_id");
    }

    @Override
    public void applyWhere(StringBuilder where, Map<String, Object> params, FilterCriteria c) {
        where.append(" AND u2.login IN (:assignees)");
        params.put("assignees", c.assignees());
    }

    @Override
    public void applyHaving(StringBuilder having, Map<String, Object> params, FilterCriteria c) {
        // no-op
    }

}
