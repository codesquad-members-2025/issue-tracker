package CodeSquad.IssueTracker.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestDto {

    @NotNull(message = "이슈 ID는 필수입니다.")
    private Long issueId;
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;
}
