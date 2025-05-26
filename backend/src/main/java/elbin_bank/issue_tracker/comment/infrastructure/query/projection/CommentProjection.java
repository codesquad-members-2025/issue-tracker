package elbin_bank.issue_tracker.comment.infrastructure.query.projection;

import java.time.LocalDateTime;

public record CommentProjection(long id,
                                long userId,
                                String content,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt) {
}
