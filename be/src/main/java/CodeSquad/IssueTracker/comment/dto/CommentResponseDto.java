package CodeSquad.IssueTracker.comment.dto;

import CodeSquad.IssueTracker.comment.Comment;
import CodeSquad.IssueTracker.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String imageUrl;
    private String authorNickname;
    private final LocalDateTime lastModifiedAt;

    public CommentResponseDto(Comment comment, User author) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.imageUrl = comment.getIssueFileUrl();
        this.authorNickname=author.getNickName();
        this.lastModifiedAt = comment.getLastModifiedAt();
    }
}

