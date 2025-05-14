package CodeSquad.IssueTracker.comment;

import lombok.Data;

@Data
public class CommentUpdateDto {
    private String content;
    private String imageUrl;
}

