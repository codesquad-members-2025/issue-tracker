package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthorFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(FilterCriteria c) {
        return c.author() != null;
    }

    @Override
    public void applyJoin(StringBuilder join, FilterCriteria c) {
        // no-op
    }

    @Override
    public void applyWhere(StringBuilder where, Map<String, Object> params, FilterCriteria c) {
        where.append(" AND a.login = :author");
        params.put("author", c.author());
    }

    @Override
    public void applyHaving(StringBuilder having, Map<String, Object> params, FilterCriteria c) {
        // no-op
    }

}
