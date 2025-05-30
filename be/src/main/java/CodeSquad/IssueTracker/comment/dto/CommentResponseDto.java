package CodeSquad.IssueTracker.comment.dto;

import CodeSquad.IssueTracker.comment.Comment;
import CodeSquad.IssueTracker.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String issueFileUrl;
    private Long authorId;
    private String authorNickname;
    private final LocalDateTime lastModifiedAt;
    private String authorProfileUrl;

    public CommentResponseDto(Comment comment, User author) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.issueFileUrl = comment.getIssueFileUrl();
        this.authorId = author.getId();
        this.authorNickname=author.getNickName();
        this.lastModifiedAt = comment.getLastModifiedAt();
        this.authorProfileUrl = author.getProfileImageUrl();
    }
}

