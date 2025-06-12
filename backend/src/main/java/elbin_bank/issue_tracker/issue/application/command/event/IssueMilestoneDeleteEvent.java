package elbin_bank.issue_tracker.issue.application.command.event;

public record IssueMilestoneDeleteEvent(
        Long milestoneId,
        boolean isClosed
) {
}
