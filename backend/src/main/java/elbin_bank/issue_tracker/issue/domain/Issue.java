package elbin_bank.issue_tracker.issue.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Issue extends BaseEntity {

    @Id
    private Long id;

    @Column("writer_id")
    private Long authorId;

    @Column("milestone_id")
    private Long milestoneId;

    private String title;

    @Column("contents")
    private String contents;

    @Column("is_closed")
    private Boolean isClosed;

    @Column("file_path")
    private String filePath;

}
