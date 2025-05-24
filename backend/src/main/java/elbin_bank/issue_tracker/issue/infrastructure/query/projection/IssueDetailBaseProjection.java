package elbin_bank.issue_tracker.issue.infrastructure.query.projection;

import java.time.LocalDateTime;

public record IssueDetailBaseProjection(long id,
                                        long authorId,
                                        String authorNickname,
                                        String authorProfileImage,
                                        String title,
                                        String contents,
                                        Long milestoneId,
                                        String milestoneName,
                                        Integer milestoneProgressRate,
                                        boolean isClosed,
                                        LocalDateTime createdAt,
                                        LocalDateTime updatedAt) {
}
