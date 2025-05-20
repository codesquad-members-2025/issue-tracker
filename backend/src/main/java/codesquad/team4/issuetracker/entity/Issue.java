package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue")
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Issue {
    @Id
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private boolean isOpen;
    private Long authorId;
    private Long milestoneId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
