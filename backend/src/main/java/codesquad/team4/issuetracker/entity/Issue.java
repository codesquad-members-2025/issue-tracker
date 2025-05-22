package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue")
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Issue {
    @Id
    @Column("issue_id")
    private Long id;

    @Column("title")
    private String title;

    @Column("content")
    private String content;

    @Column("file_url")
    private String FileUrl;

    @Column("is_open")
    private boolean isOpen;

    @Column("author_id")
    private Long authorId;

    @Column("milestone_id")
    private Long milestoneId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

}
