package codesquad.team01.issuetracker.issue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("issue")
public class Issue {

    @Id
    private Long id;

    private String title;
    private String content;

    @Column("is_open")
    private boolean isOpen;

    @Column("created_id")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("creator_id")
    private Long creatorId;

    @Column("milestone_id")
    private Long milestoneId;

    protected Issue() {}

    public Issue(Long id, String title, String content, boolean isOpen, LocalDateTime createdAt, LocalDateTime updatedAt, Long creatorId, Long milestoneId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isOpen = isOpen;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.creatorId = creatorId;
        this.milestoneId = milestoneId;
    }
}
