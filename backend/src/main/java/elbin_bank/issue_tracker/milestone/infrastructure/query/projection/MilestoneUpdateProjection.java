package elbin_bank.issue_tracker.milestone.infrastructure.query.projection;

public record MilestoneUpdateProjection(long id, String title, String expiredAt, String description) {
}
