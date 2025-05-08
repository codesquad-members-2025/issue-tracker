package CodeSquad.IssueTracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("issues")
public class Issue {
    @Id
    private Long id;
    private String title;
    private Boolean isOpen;
    private Integer issueNumber;
    private LocalDateTime timestamp;

    private Long authorId;
    private Long assigneeId;
    private Long milestoneId;
}

