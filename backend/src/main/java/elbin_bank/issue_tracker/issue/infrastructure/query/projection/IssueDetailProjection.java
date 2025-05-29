package elbin_bank.issue_tracker.issue.infrastructure.query.projection;

import java.time.LocalDateTime;

public record IssueDetailProjection(long id,
                                    long authorId,
                                    String authorNickname,
                                    String authorProfileImage,
                                    String title,
                                    String contents,
                                    boolean isClosed,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt) {
}
