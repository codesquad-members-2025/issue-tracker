package codesquad.team01.issuetracker.issue.dto;

import codesquad.team01.issuetracker.label.dto.LabelSimpleResponse;
import codesquad.team01.issuetracker.milestone.dto.MilestoneSimpleResponse;
import codesquad.team01.issuetracker.user.dto.UserResponse;
import codesquad.team01.issuetracker.user.dto.UserSimpleResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class IssueSimpleResponse {
    private final Long id;
    private final String title;
    private final Boolean isOpen;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UserResponse writer;
    private final MilestoneSimpleResponse milestone;
    @Builder.Default
    private final List<UserSimpleResponse> assignees = new ArrayList<>();
    @Builder.Default
    private final List<LabelSimpleResponse> labels = new ArrayList<>();
}
