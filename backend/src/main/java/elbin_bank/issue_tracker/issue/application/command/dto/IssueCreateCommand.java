package elbin_bank.issue_tracker.issue.application.command.dto;

import java.util.List;

public record IssueCreateCommand(
        Long authorId,
        String title,
        String content,
        List<Long> assignees,
        List<Long> labels,
        Long milestone
) {
}
