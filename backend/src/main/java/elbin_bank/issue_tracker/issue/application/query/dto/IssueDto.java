package elbin_bank.issue_tracker.issue.application.query.dto;

import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;

import java.time.LocalDateTime;
import java.util.List;

public record IssueDto(long id,
                       String author,
                       String title,
                       boolean isClosed,
                       List<LabelProjection> labels,
                       List<String> assigneesProfileImages,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       String milestone) {
}
