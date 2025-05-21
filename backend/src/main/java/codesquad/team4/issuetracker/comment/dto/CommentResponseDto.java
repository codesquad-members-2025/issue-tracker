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
    public static class CommentInfo {
        private Long commentId;
        private String content;
        private UserDto.UserInfo author;
        private String imageUrl;
        private LocalDateTime createdAt;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class CreateCommentDto {
        private Long id;
        private String message;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class UpdateCommentDto {
        private Long id;
        private String message;
    }
}
