package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class LabelFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(FilterCriteria c) {
        return !c.labels().isEmpty();
    }

    @Override
    public void appendWhere(StringBuilder where,
                            MapSqlParameterSource p,
                            FilterCriteria c) {
        where.append(" AND lb.name IN (:labels)");
        p.addValue("labels", c.labels());
    }

    @Override
    public void appendHaving(StringBuilder having,
                             MapSqlParameterSource p,
                             FilterCriteria c) {
        // HAVING 절은 "HAVING" 키워드로 시작하도록
        having.append("""
           HAVING COUNT(DISTINCT CASE
               WHEN lb.name IN (:labels) THEN lb.name END
           ) = :needLbl
        """);
        p.addValue("needLbl", c.labels().size());
    }

}
