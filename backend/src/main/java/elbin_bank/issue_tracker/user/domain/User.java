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
    private Long id;

    @Column("github_id")
    private Long githubId;

    @Column("login")
    private String login;

    @Column("password")
    private String password;

    @Column("nickname")
    private String nickname;

    @Column("profile_image_url")
    private String profileImageUrl;

    @Column("uuid")
    private String uuid;

}
