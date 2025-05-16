package CodeSquad.IssueTracker.comment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("comments")
public class Comment {
    @Id
    private Long commentId;
    private Long issueId;       // ✅ 이슈 ID: 이 댓글이 속한 이슈
    private String content;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

