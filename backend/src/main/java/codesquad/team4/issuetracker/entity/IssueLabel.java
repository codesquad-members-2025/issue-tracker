package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue_label")
@AllArgsConstructor
@Getter
@Builder
public class IssueLabel {
    @Id
    @Column("issue_label_id")
    private Long id;

    @Column("issue_id")
    private Long issueId;

    @Column("label_id")
    private Long labelId;

    @Column("created_at")
    private LocalDateTime createdAt;
}
