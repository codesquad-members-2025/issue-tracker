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
    private Long githubId;
    private String login;
    private String password;
    private String salt;
    private String nickname;
    private String profileImageUrl;
    private String uuid;

    public static User createByLogin(String login, String password, String salt, String nickname, String profileImageUrl, String uuid) {
        return new User(
                null, // ID는 DB에서 자동 생성되므로 null으로 초기화
                null, // github login은 아니므로 null
                login,
                password,
                salt,
                nickname,
                profileImageUrl,
                uuid
        );
    }

    public static User createByOauth(Long githubId, String nickname, String profileImageUrl, String uuid) {
        return new User(
                null, // ID는 DB에서 자동 생성되므로 null으로 초기화
                githubId,
                null, // 로그인은 OAuth 로그인에서는 사용하지 않으므로 null
                null, // 비밀번호는 OAuth 로그인에서는 사용하지 않으므로 null
                null, // salt는 필요하지 않으므로 null
                nickname,
                profileImageUrl,
                uuid
        );
    }

}
