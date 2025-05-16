package CodeSquad.IssueTracker.issue;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("issues")
public class Issue {
    @Id
    private Long issueId;
    private String title;
    private String content;
    private Long authorId;
    private Long milestoneId;
    private Boolean isOpen;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


