package CodeSquad.IssueTracker.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@Table("users")
public class User {
    @Id
    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String nickName;
}

