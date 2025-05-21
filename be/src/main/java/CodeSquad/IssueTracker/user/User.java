package CodeSquad.IssueTracker.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class User {
    @Id
    private Long id;
    private String loginId; //이메일 형태로 주입받기
    private String password;
    private String nickName;
    private String profileImageUrl;
}
