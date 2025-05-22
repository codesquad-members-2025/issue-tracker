package codesquad.team01.issuetracker.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

//todo: DDL 작성, loginId -> id로
//todo: token을 안전하게 저장해야함(ex. 암호화 등)
@Getter
@AllArgsConstructor
@Table("refresh_tokens")
public class RefreshToken {
	@Id
	private Integer id;
	private Integer userId;
	private String token; //암호화 해서 db에 저장해야 함!
}