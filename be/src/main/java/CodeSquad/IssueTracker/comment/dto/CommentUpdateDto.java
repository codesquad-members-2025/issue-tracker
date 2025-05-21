package CodeSquad.IssueTracker.comment.dto;

import lombok.Data;

@Data
public class CommentUpdateDto {
    private String content;
    private String imageUrl;
}

