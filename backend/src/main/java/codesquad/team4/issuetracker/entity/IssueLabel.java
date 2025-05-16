package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue_label")
@AllArgsConstructor
@Getter
@Builder
public class IssueLabel {
    @Id
    private Long id;
    private Long issueId;
    private Long labelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
