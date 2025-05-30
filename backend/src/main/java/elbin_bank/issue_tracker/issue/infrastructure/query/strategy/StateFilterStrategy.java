package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

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
    public void applyWhere(StringBuilder where, MapSqlParameterSource params, FilterCriteria c) {
        // 이미 isClosed 조건이 항상 들어가도록 설정되어 있으므로, 별도의 where 절은 필요하지 않음
        params.addValue("isClosed", c.isClosed());
    }

    @Override
    public void applyHaving(StringBuilder having, MapSqlParameterSource params, FilterCriteria c) {
        // no-op
    }

}
