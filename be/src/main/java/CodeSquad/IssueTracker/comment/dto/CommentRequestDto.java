package CodeSquad.IssueTracker.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long issueId;
    private String content;
    private String imageUrl;

    public CommentRequestDto(Long issueId, String content, String imageUrl) {
        this.issueId = issueId;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
