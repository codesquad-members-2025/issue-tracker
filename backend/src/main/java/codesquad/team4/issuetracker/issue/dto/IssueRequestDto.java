package codesquad.team4.issuetracker.issue.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class IssueRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class CreateIssueDto{
        @NotNull
        private String title;
        @NotNull
        private String content;
        @NotNull
        private Long authorId;
        private List<Long> assigneeId;
        private List<Long> labelId;
        private Long milestoneId;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class BulkUpdateIssueStatusDto {
        private List<Long> issuesId;
        private boolean isOpen;
    }
}
