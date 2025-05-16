package CodeSquad.IssueTracker.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("users")
public class User {
    @Id
    private Long id;
    private String loginId;
    private String password;
    private String emailId;
    private String nickName;
}
