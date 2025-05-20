package codesquad.team4.issuetracker.issue.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
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
        private Set<Long> assigneeId;
        private Set<Long> labelId;
        private Long milestoneId;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class BulkUpdateIssueStatusDto {
        private List<Long> issuesId;
        private boolean isOpen;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueUpdateDto {
        private String title;
        private String content;
        private Long milestoneId;
        private Boolean isOpen;
        private Boolean removeImage;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueLabelsUpdateDto {
        private Set<Long> labels;
    }
}
