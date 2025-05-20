package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
@AllArgsConstructor
@Getter
@Builder
public class User {
    @Id
    @Column("user_id")
    private Long id;

    @Column("email")
    private String email;

    @Column("nickname")
    private String nickname;

    @Column("profile_image")
    private String profileImage;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
