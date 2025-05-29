package elbin_bank.issue_tracker.issue.infrastructure.query.projection;

import java.time.LocalDateTime;

public record IssueProjection(long id,
                              String author,
                              String title,
                              boolean isClosed,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              String milestone
) {
}
