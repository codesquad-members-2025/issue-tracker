package elbin_bank.issue_tracker.issue.infrastructure.query;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public record SqlClauseResult(String where,
                              String having,
                              MapSqlParameterSource params
) {
}
