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
    public void applyJoin(StringBuilder join, FilterCriteria c) {
        join.append(" JOIN issue_label il ON il.issue_id = i.id")
                .append(" JOIN label l ON l.id = il.label_id");
    }

    @Override
    public void applyWhere(StringBuilder where, MapSqlParameterSource params, FilterCriteria c) {
        where.append(" AND l.name IN (:labels)");
        params.addValue("labels", c.labels());
    }

    @Override
    public void applyHaving(StringBuilder having, MapSqlParameterSource params, FilterCriteria c) {
        // 교집합: 반드시 필터 개수만큼 매칭된 레이블 수와 같아야 함
        having.append(" AND COUNT(DISTINCT l.name) = :labelCount");
        params.addValue("labelCount", c.labels().size());
    }

}
