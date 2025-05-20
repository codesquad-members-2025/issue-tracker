package codesquad.team01.issuetracker.issue.repository;

import java.util.List;

import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.issue.dto.IssueDto;

public interface IssueQueryRepository {

	List<IssueDto.BaseRow> findIssuesWithFilters(
		IssueState state, Long writerId, Long milestoneId,
		List<Long> labelIds, List<Long> assigneeIds);

}
