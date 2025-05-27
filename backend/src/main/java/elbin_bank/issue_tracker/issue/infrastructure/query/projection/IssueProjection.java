package elbin_bank.issue_tracker.issue.infrastructure.query.projection;

import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;

import java.time.LocalDateTime;
import java.util.List;

public record IssueProjection(long id,
                              String author,
                              String title,
                              List<LabelProjection> labels,
                              boolean isClosed,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              List<String> assigneesProfileImages,
                              String milestone
) {
}
