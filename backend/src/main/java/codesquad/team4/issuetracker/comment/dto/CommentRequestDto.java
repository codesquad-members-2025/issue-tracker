package codesquad.team4.issuetracker.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.annotations.NotNull;

public class CommentRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class CreateCommentDto {
        @NotNull
        private String content;
        @NotNull
        private Long issueId;
        @NotNull
        private Long authorId;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class UpdateCommentDto {
        @NotEmpty
        private String content;
    }
}
