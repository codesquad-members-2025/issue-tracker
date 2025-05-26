package codesquad.team4.issuetracker.issue.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

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
        private Set<Long> assigneeIds;
        private Set<Long> labelIds;
        private Long milestoneId;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class BulkUpdateIssueStatusDto {
        @NotEmpty
        private List<Long> issuesId;
        @NotNull
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

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueAssigneeUpdateDto {
        private Set<Long> assignees;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueFilterParamDto {
        private Long authorId;
        private Long assigneeId;
        private Long commentAuthorId;
        private Long milestoneId;
        private OpenStatus status;
        private List<Long> labelIds;

        @Getter
        public enum OpenStatus{
            OPEN("open", true), CLOSE("close", false);

            final String value;
            final boolean state;

            OpenStatus(String value, boolean state) {
                this.value = value;
                this.state = state;
            }
            public static OpenStatus fromValue(String value) {
                for (OpenStatus status : OpenStatus.values()) {
                    if (status.value.equalsIgnoreCase(value)) {
                        return status;
                    }
                }
                return OpenStatus.OPEN;
            }
            public boolean getState() {
                return state;
            }
        }
    }
}
