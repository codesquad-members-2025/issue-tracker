package elbin_bank.issue_tracker.issue.presentation.command.dto.request;

import java.util.List;

public record IssueCreateRequestDto(
        String title,
        String content,
        List<Long> assignees,
        List<Long> labels,
        Long milestone
) {
}
