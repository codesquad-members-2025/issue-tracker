package elbin_bank.issue_tracker.issue.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue")
@AllArgsConstructor
@Getter
@Setter
public class Issue extends BaseEntity {

    @Id
    private final Long id;
    private final long authorId;
    private String title;
    private String contents;
    private Long milestoneId;
    private boolean isClosed;

    public static Issue of(long id, long authorId, String title, String contents, Long milestoneId) {
        return new Issue(id, authorId, title, contents, milestoneId, false);
    }

    public static Issue of(long authorId, String title, String contents, Long milestoneId, boolean isClosed) {
        return new Issue(null, authorId, title, contents, milestoneId, isClosed);
    }

    public void changeState(boolean isClosed) {
        this.isClosed = isClosed;
    }

}
