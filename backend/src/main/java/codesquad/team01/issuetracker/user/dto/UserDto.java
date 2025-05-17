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
    public record Response(
            Long id,
            String username,
            String profileImageUrl
    ) {
    }

    // 담당자 응답 DTO
    @Builder
    public record SimpleResponse(
            Long id,
            String profileImageUrl
    ) {
    }
}
