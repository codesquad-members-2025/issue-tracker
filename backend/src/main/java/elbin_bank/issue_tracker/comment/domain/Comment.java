package elbin_bank.issue_tracker.comment.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    private long id;

    @Column("issue_id")
    private long issueId;

    @Column("user_id")
    private long userId;

    private String contents;

    @Column("file_path")
    private String filePath;

}
