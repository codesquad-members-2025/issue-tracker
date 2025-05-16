package CodeSquad.IssueTracker.comment.dto;

import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private String content;
    private String authorNickname;
    private String createdAt;
    private String updatedAt;
}

