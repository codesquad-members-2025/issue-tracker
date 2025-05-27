package elbin_bank.issue_tracker.issue.application.query.dsl;

import java.util.List;

public record FilterCriteria(
        List<Boolean> states,
        List<String> labels,
        List<String> assignees,
        List<String> authors,
        List<String> milestones
) {
    public static FilterCriteria empty() {
        return new FilterCriteria(List.of(false), List.of(), List.of(), List.of(), List.of());
    }
}
