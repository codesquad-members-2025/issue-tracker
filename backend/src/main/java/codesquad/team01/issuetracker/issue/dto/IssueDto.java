package codesquad.team01.issuetracker.issue.dto;

import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team01.issuetracker.user.dto.UserDto;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IssueDto {

    private IssueDto() {
    }

    /**
     * 요청 DTO
     */
    // 조회 필터 쿼리 요청 DTO
    public record QueryRequest(
            @Pattern(regexp = "^(open|closed)$", message = "state는 'open' 또는 'closed'만 가능합니다")
            String state,
            Long writer,
            Long milestone,
            List<Long> labels,
            List<Long> assignees
    ) {
    }

    /**
     * 응답 DTO
     */
    // 이슈 목록 응답 DTO
    @Builder
    public record ListResponse(
            int totalCount,
            List<SimpleResponse> issues
    ) {
    }

    // 이슈 목록 중 하나의 이슈 응답 DTO
    @Getter
    @Builder(toBuilder = true)
    public static class SimpleResponse {
        private final Long id;
        private final String title;
        private final Boolean isOpen;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final UserDto.Response writer;
        private final MilestoneDto.SimpleResponse milestone;

        @Builder.Default
        private final List<UserDto.SimpleResponse> assignees = new ArrayList<>();

        @Builder.Default
        private final List<LabelDto.SimpleResponse> labels = new ArrayList<>();
    }

    /**
     * DB 조회용 DTO
     */
    // 이슈 기본 정보 행 DTO
    @Builder
    public record BaseRow(
            // issue
            Long issueId,
            String issueTitle,
            Boolean issueOpen,
            LocalDateTime issueCreatedAt,
            LocalDateTime issueUpdatedAt,

            // writer
            Long writerId,
            String writerUsername,
            String writerProfileImageUrl,

            // milestone - nullable
            Long milestoneId,
            String milestoneTitle
    ) {
    }

    // 이슈 담당자 행 DTO
    @Builder
    public record AssigneeRow(
            Long issueId,
            Long assigneeId,
            String assigneeProfileImageUrl
    ) {
    }

    // 이슈 레이블 행 DTO
    @Builder
    public record LabelRow(
            Long issueId,
            Long labelId,
            String labelName,
            String labelColor,
            String labelTextColor
    ) {
    }

    /**
     * 서비스 계층 DTO
     */
    // 이슈 상세 정보 DTO
    @Builder
    public record Details(
            BaseRow baseInfo,
            List<UserDto.SimpleResponse> assignees,
            List<LabelDto.SimpleResponse> labels
    ) {
        public SimpleResponse toSimpleResponse() { // Mapper 클래스로 따로 뺄지 고민
            return SimpleResponse.builder()
                    .id(baseInfo.issueId())
                    .title(baseInfo.issueTitle())
                    .isOpen(baseInfo.issueOpen())
                    .createdAt(baseInfo.issueCreatedAt())
                    .updatedAt(baseInfo.issueUpdatedAt())
                    .writer(UserDto.Response.builder()
                            .id(baseInfo.writerId())
                            .username(baseInfo.writerUsername())
                            .profileImageUrl(baseInfo.writerProfileImageUrl())
                            .build()
                    )
                    .milestone(baseInfo.milestoneId() != null
                            ? MilestoneDto.SimpleResponse.builder()
                            .id(baseInfo.milestoneId())
                            .title(baseInfo.milestoneTitle())
                            .build()
                            : null)
                    .assignees(assignees)
                    .labels(labels)
                    .build();
        }
    }
}