package elbin_bank.issue_tracker.issue.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue")
@AllArgsConstructor
@Getter
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

    public void changeState(boolean targetClosed) {
        if (this.isClosed == targetClosed) {
            return;
        }

        this.isClosed = targetClosed;
    }

    public void changeTitle(String title) {
        if (this.title.equals(title)) {
            return;
        }

        this.title = title;
    }

    public void changeContents(String contents) {
        if (this.contents.equals(contents)) {
            return;
        }

        this.contents = contents;
    }

    public void changeMilestone(Long milestoneId) {
        if (this.milestoneId != null && this.milestoneId.equals(milestoneId)) {
            return;
        }

        this.milestoneId = milestoneId;
    }

}
