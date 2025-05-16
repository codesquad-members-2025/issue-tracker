package codesquad.team01.issuetracker.issue.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record IssueListResponse(
        int totalCount,
        List<IssueSimpleResponse> issues
) {
}
