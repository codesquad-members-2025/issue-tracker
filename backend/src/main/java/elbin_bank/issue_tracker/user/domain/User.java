package elbin_bank.issue_tracker.user.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    private long id;
    private Long githubId;
    private String login;
    private String password;
    private String nickname;
    private String profileImageUrl;
    private String uuid;

}
