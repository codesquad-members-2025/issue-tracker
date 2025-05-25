package elbin_bank.issue_tracker.issue.application.query.dto;

import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;

import java.time.LocalDateTime;
import java.util.List;

public record IssueDetailResponseDto(Long id,
                                     UserInfoProjection author,
                                     String title,
                                     String contents,
                                     List<LabelProjection> labels,
                                     MilestoneDto milestone,
                                     List<UserInfoProjection> assignees,
                                     Boolean isClosed,
                                     LocalDateTime createdAt,
                                     LocalDateTime updatedAt
) {
}
