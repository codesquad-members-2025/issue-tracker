package codesquad.team01.issuetracker.issue.domain;

import codesquad.team01.issuetracker.common.domain.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("Issue")
public class Issue extends BaseEntity {

    @Id
    private Long id;

    private String title;
    private String content;

    private boolean isOpen = true;

    private LocalDateTime closedAt;

    private Long writerId;

    private Long milestoneId;

    @Builder
    private Issue(Long id, String title, String content, boolean isOpen,
                  LocalDateTime closedAt, Long writerId, Long milestoneId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isOpen = isOpen;
        this.closedAt = closedAt;
        this.writerId = writerId;
        this.milestoneId = milestoneId;
    }
}
