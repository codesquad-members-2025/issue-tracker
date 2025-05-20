package codesquad.team01.issuetracker.user.repository;

import java.util.List;

import codesquad.team01.issuetracker.user.dto.UserDto;

public interface UserQueryRepository {

	List<UserDto.IssueAssigneeRow> findAssigneesByIssueIds(List<Long> issueIds);
}
