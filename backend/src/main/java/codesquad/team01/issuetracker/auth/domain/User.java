package codesquad.team01.issuetracker.auth.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

	@Id
	private Integer id;

	@Column("login_id")
	private String loginId;

	private String username;

	private String email;

	private String password;

	@CreatedDate
	@Column("created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column("updated_at")
	private LocalDateTime updatedAt;

	@Column("provider_id")
	private Long providerId;

	@Column("auth_provider")
	private String authProvider; //local or github

	@Column("profile_image_url")
	private String profileImageUrl;

	@Column("deleted_at")
	private LocalDateTime deletedAt;

}
