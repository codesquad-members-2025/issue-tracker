package elbin_bank.issue_tracker.issue.infrastructure.query.projection;

import java.time.LocalDateTime;

public record IssueDetailProjection(long id,
                                    long authorId,
                                    String authorNickname,
                                    String authorProfileImage,
                                    String title,
                                    String contents,
                                    Long milestoneId,
                                    String milestoneName,
                                    long milestoneTotalIssues,
                                    long milestoneClosedIssues,
                                    boolean isClosed,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt) {
}
