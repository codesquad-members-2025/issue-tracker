package CodeSquad.IssueTracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
    @Id
    private Long id;
    private String loginId;
    private String password;
    private String email;
}
