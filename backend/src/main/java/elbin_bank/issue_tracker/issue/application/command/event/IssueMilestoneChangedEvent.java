package elbin_bank.issue_tracker.issue.application.command.event;

public record IssueMilestoneChangedEvent(
        Long oldMilestoneId,    // null이면 원래 마일스톤이 없었음을 의미
        Long newMilestoneId,    // null이면 새 마일스톤을 없앴음을 의미
        boolean isClosed
) {
}
