package elbin_bank.issue_tracker.comment.application.query.dto;

import elbin_bank.issue_tracker.user.infrastructure.query.projection.UserProjection;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        UserProjection author,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
