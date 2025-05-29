package elbin_bank.issue_tracker.issue.application.query.dsl;

import java.util.List;

public record FilterCriteria(
        boolean isClosed,
        List<String> labels,
        List<String> assignees,
        String author,
        String milestone,
        boolean isInvalidFilter
) {
}
