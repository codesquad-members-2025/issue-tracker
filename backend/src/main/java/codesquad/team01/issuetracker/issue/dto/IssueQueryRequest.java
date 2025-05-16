package codesquad.team01.issuetracker.issue.dto;


import jakarta.validation.constraints.Pattern;

import java.util.List;

public record IssueQueryRequest(

    @Pattern(regexp = "^(open|closed)$", message = "state는 'open' 또는 'closed'만 가능합니다")
    String state,
    Long writer,
    Long milestone,
    List<Long> labels,
    List<Long> assignees
) {
}
