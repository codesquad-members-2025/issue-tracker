package codesquad.team01.issuetracker.issue.dto;

import java.time.LocalDateTime;

public record IssueSimpleResponse(
        Long id,
        String title,
        Boolean isOpen,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserSimpleResponse writer,
        List<UserSimpleResponse> assignees,
        List<LabelSimpleResponse> labels,
        MilestoneSimpleResponse milestone
) {
}
