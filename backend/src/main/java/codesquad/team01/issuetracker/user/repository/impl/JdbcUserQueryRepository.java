package codesquad.team01.issuetracker.user.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.user.dto.UserDto;
import codesquad.team01.issuetracker.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcUserQueryRepository implements UserQueryRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private static final String ASSIGNEE_QUERY = """
		    SELECT 
		        ia.issue_id,
		        u.id as assignee_id,
		        f.url as assignee_profile_image_url
		    FROM issue_assignee ia
		    JOIN users u ON ia.user_id = u.id
		    LEFT JOIN file f ON u.profile_image_id = f.id
		    WHERE ia.issue_id IN (:issueIds)
		""";

	private final RowMapper<UserDto.IssueAssigneeRow> assigneeRowMapper = (rs, rowNum) -> UserDto.IssueAssigneeRow.builder()
		.issueId(rs.getInt("issue_id"))
		.assigneeId(rs.getInt("assignee_id"))
		.assigneeProfileImageUrl(rs.getString("assignee_profile_image_url"))
		.build();

	@Override
	public List<UserDto.IssueAssigneeRow> findAssigneesByIssueIds(List<Integer> issueIds) {
		if (issueIds.isEmpty()) {
			return List.of();
		}
		MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);
		return jdbcTemplate.query(ASSIGNEE_QUERY, params, assigneeRowMapper);
	}
}
