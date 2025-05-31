package CodeSquad.IssueTracker.comment;

import CodeSquad.IssueTracker.global.exception.NoAuthorityException;
import CodeSquad.IssueTracker.global.exception.NoParametersException;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Table("comments")
public class Comment {
    @Id
    private Long commentId;
    private Long issueId;       // ✅ 이슈 ID: 이 댓글이 속한 이슈
    private String content;
    private Long authorId;
    private LocalDateTime lastModifiedAt;
    private String issueFileUrl;

    public Comment() {
    }

    public Comment(Long commentId, Long issueId, String content, Long authorId, LocalDateTime lastModifiedAt) {
        this.commentId = commentId;
        this.issueId = issueId;
        this.content = content;
        this.authorId = authorId;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static Comment createComment(Long issueId, String content, Long authorId) {
        if (content == null || content.isBlank()) {
            throw new NoParametersException("댓글");
        }
        return new Comment(null, issueId, content, authorId, LocalDateTime.now());
    }

    private void verifyAuthor(Long requesterId) {
        if (!this.authorId.equals(requesterId)) {
            throw new NoAuthorityException();
        }
    }

    public void update(String content, Long requesterId) {
        verifyAuthor(requesterId);
        this.content = content;
        this.lastModifiedAt = LocalDateTime.now();
    }


}

