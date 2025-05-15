package codesquad.team4.issuetracker.issue.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class IssueRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class CreateIssueDto{
        @NonNull
        private String title;
        @NonNull
        private String content;
        @NonNull
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
