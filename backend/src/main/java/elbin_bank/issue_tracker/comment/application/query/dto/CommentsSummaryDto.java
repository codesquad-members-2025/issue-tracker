package elbin_bank.issue_tracker.comment.application.query.dto;

import java.time.LocalDateTime;

public record CommentsSummaryDto(
        Long id,
        UserDto author,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {
}
