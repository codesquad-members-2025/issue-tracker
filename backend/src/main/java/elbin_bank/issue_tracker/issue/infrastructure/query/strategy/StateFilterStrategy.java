package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StateFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(FilterCriteria c) {
        // 항상 isClosed 조건은 들어가도록
        return true;
    }

    @Override
    public void applyJoin(StringBuilder join, FilterCriteria c) {
        // no-op
    }

    @Override
    public void applyWhere(StringBuilder where, Map<String, Object> params, FilterCriteria c) {
        where.append(" AND i.is_closed = :isClosed");
        params.put("isClosed", c.isClosed());
    }

    @Override
    public void applyHaving(StringBuilder having, Map<String, Object> params, FilterCriteria c) {
        // no-op
    }

}
