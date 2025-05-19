package codesquad.team01.issuetracker.user.repository;

import codesquad.team01.issuetracker.user.dto.UserDto;

import java.util.List;

public interface UserQueryRepository {

    List<UserDto.IssueAssigneeRow> findAssigneesByIssueIds(List<Long> issueIds);
}
