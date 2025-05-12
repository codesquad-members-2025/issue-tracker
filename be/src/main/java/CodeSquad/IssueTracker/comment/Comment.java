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
    private String content;
    private String imageUrl;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
