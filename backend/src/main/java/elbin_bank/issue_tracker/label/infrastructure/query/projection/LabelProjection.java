package elbin_bank.issue_tracker.label.infrastructure.query.projection;

public record LabelProjection(long id,
                              String name,
                              String color,
                              String description) {
}
