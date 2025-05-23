package codesquad.team01.issuetracker.issue.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.issue.dto.IssueDto;

@Repository
public interface IssueQueryRepository {

	int PAGE_SIZE = 10;

	List<IssueDto.BaseRow> findIssuesWithFilters(
		IssueState state, Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds, IssueDto.CursorData cursor);

	IssueDto.CountResponse countIssuesWithFilters(Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds);
}
