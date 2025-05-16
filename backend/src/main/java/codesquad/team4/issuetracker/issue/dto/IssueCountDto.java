package codesquad.team4.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class IssueCountDto {
    private Integer openCount;
    private Integer closedCount;
}
