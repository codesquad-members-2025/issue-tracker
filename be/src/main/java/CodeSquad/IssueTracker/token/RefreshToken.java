package CodeSquad.IssueTracker.token;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@Table("refresh_tokens")
public class RefreshToken {
    @Id
    private String loginId;
    private String refreshToken;
    private long expiration;
}
