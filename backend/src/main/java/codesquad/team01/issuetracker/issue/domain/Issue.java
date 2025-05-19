package codesquad.team01.issuetracker.issue.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table("Issue")
public class Issue {

    @Id
    private Long id;

    private String title;
    private String content;

    @Builder.Default
    private boolean isOpen = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime closedAt;

    private Long writerId;

    private Long milestoneId;
}
