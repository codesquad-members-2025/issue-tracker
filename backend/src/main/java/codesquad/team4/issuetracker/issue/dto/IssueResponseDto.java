package codesquad.team4.issuetracker.issue.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class IssueResponseDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueListDto{
        private List<IssueInfo> issues;
        private Integer page;
        private Integer size;
        private Integer totalPages;
        private Integer totalElements;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueInfo{
        private Long id;
        private String title;
        private UserInfo author;
        private List<LabelInfo> labels;
        private List<UserInfo> assignees;
        private Long milestoneId;
        private String milestoneTitle;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class LabelInfo{
        private Long id;
        private String name;
        private String color;
    }
    @AllArgsConstructor
    @Getter
    @Builder
    public static class UserInfo{
        private Long id;
        private String name;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class CreateIssueDto {
        private Long id;
        private String messqge;
    }
}
