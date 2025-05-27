package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface FilterStrategy {

    boolean supports(FilterCriteria c);

    void appendWhere(StringBuilder where,
                     MapSqlParameterSource p,
                     FilterCriteria c);

    default void appendHaving(StringBuilder having,
                              MapSqlParameterSource p,
                              FilterCriteria c) {
    }

}
