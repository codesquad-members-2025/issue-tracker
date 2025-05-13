package codesquad.team4.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class IssueRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class CreateIssueDto {
        private Long id;
        private String messqge;
    }
}
