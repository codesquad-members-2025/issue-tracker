package CodeSquad.IssueTracker.comment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("comment_images")
public class CommentImage {
    @Id
    private Long id;

    private Long commentId;
    private String imageUrl;
    private String originalName;
    private String contentType;
}
