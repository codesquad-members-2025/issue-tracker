package codesquad.team01.issuetracker.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "loginId", "username"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column("login_id")
    private String loginId;

    private String username;

    private String email;

    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String authProvider;

    @Builder
    public User(String loginId,
                String username,
                String email,
                String password,
                String authProvider) {
        this.loginId = loginId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authProvider = authProvider;
    }
}
