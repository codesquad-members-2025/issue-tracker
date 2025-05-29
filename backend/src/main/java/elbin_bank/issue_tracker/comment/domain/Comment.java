package elbin_bank.issue_tracker.comment.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    private Long id;
    private long issueId;
    private long userId;
    private String contents;

    public static Comment of(long issueId, long userId, String contents) {
        return new Comment(null, issueId, userId, contents); // id는 DB에서 생성
    }

}
