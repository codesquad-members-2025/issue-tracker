package codesquad.team01.issuetracker.issue.domain;

import codesquad.team01.issuetracker.common.domain.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("issue")
public class Issue extends BaseEntity {

    @Id
    private Long id;

    private String title;
    private String content;

    private IssueState state = IssueState.OPEN;

    private LocalDateTime closedAt;

    private Long writerId;

    private Long milestoneId;

    @Builder
    private Issue(Long id, String title, String content, IssueState state,
                  LocalDateTime closedAt, Long writerId, Long milestoneId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state != null ? state : IssueState.OPEN; // 생성자에 들어가야하는지? 생성될 때 기본으로 open 아닌지? 이슈 생성 작업 때 고민해보자
        this.closedAt = closedAt;
        this.writerId = writerId;
        this.milestoneId = milestoneId;
    }
}
