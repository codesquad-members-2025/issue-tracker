package codesquad.team01.issuetracker.user.dto;

import lombok.Builder;

public class UserDto {

    private UserDto() {
    }

    /**
     * 응답 DTO
     */
    // 이슈 목록 - 작성자 응답 DTO
    @Builder
    public record WriterResponse(
            Long id,
            String username,
            String profileImageUrl
    ) {
    }

    // 담당자 응답 DTO
    @Builder
    public record AssigneeResponse(
            Long id,
            String profileImageUrl
    ) {
    }

    /**
     * DB 조회용 DTO
     */
    // 이슈 담당자 행 DTO
    @Builder
    public record IssueAssigneeRow(
            Long issueId,
            Long assigneeId,
            String assigneeProfileImageUrl
    ) {
    }
}
