package codesquad.team01.issuetracker.issue.dto;

import lombok.Builder;

@Builder
public record IssueListResponse(
        int totalCount,
        List<IssueDetailResponse> issues
) {
}
