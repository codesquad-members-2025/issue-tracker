package codesquad.team4.issuetracker.issue.dto;

import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto.LabelInfo;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class IssueResponseDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueListDto {
        private List<IssueInfo> issues;
        private Integer page;
        private Integer size;
        private Integer totalPages;
        private Integer totalElements;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class IssueInfo {
        private Long id;
        private String title;
        private UserDto.UserInfo author;
        private Set<LabelInfo> labels;
        private Set<UserDto.UserInfo> assignees;
        private MilestoneResponseDto.MilestoneInfo milestone;
        private String createdAt;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class ApiMessageDto {
        private Long id;
        private String message;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class BulkUpdateIssueStatusDto {
        private List<Long> issuesId;
        private String message;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class searchIssueDetailDto {
        private String content;
        private String contentFileUrl;
        private List<CommentResponseDto.CommentInfo> comments;
        private Integer commentSize;
    }
}
