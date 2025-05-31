package elbin_bank.issue_tracker.issue.presentation.command.dto.request;

import java.util.List;

public record IssueLabelsUpdateRequestDto(
        List<Long> labels
) {
}
