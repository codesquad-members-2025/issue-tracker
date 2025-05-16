package codesquad.team01.issuetracker.milestone.dto;

import lombok.Builder;

@Builder
public record MilestoneSimpleResponse(
        Long id,
        String title
) {
}
