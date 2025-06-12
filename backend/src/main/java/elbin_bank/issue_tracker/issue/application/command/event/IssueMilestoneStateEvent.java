package elbin_bank.issue_tracker.issue.application.command.event;

public record IssueMilestoneStateEvent(
        Long milestoneId,
        boolean isClosed
) {
}
