package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue")
@AllArgsConstructor
@Getter
public class Issue {
    @Id
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private boolean isOpen;
    private Long authorId;
    private Long milestoneId;
    private Long commentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
