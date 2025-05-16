package codesquad.team01.issuetracker.issue.dto;

import java.time.LocalDateTime;

public record IssueJoinRow(

        // issue
        Long issueId,
        String issueTitle,
        Boolean issueOpen,
        LocalDateTime issueCreatedAt,
        LocalDateTime issueUpdatedAt,

        // writer
        Long writerId,
        String writerUsername,
        String writerProfileImageUrl,

        // milestone - nullable
        Long milestoneId,
        String milestoneTitle,

        // assignee - nullable
        Long assigneeId,
        String assigneeProfileImageUrl,

        // label - nullable
        Long labelId,
        String labelName,
        String labelColor,
        String labelTextColor
) {
}
