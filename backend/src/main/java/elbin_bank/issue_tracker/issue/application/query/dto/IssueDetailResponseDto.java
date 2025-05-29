package elbin_bank.issue_tracker.issue.application.query.dto;

import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;

import java.time.LocalDateTime;

public record IssueDetailResponseDto(Long id,
                                     UserInfoProjection author,
                                     String title,
                                     String contents,
                                     boolean isClosed,
                                     LocalDateTime createdAt,
                                     LocalDateTime updatedAt
) {
}
