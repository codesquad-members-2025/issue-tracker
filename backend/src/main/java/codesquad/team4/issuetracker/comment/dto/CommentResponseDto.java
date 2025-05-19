package codesquad.team4.issuetracker.comment.dto;

import codesquad.team4.issuetracker.user.dto.UserDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CommentResponseDto {
    @AllArgsConstructor
    @Getter
    @Builder
    public static class commentInfo {
        private Long commentId;
        private String content;
        private UserDto.UserInfo author;
        private String imageUrl;
        private LocalDateTime createdAt;
    }
}
