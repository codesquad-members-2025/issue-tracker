package codesquad.team01.issuetracker.user.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table("users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "loginId", "username"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@EqualsAndHashCode.Include
	private Integer id;

	@Column("login_id")
	private String loginId;

	private String username;

	private String email;

	private String password;

	private Long providerId;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private String authProvider;

	private String profileImageUrl;

	@Builder
	public User(String loginId,
		String username,
		String email,
		String password,
		Long providerId,
		String authProvider) {
		this.loginId = loginId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.providerId = providerId;
		this.authProvider = authProvider;
	}
}
