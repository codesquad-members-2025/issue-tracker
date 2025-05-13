package codesquad.team4.issuetracker.issue.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class IssueRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class CreateIssueDto{
        private String title;
        private String content;
        private Long authorId;
        private List<Long> assigneeId;
        private List<Long> labelId;
        private Long milestoneId;
    }
}
