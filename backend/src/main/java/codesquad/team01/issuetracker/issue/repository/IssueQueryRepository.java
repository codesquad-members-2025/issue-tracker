package codesquad.team01.issuetracker.issue.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.common.dto.CursorDto;
import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.issue.dto.IssueDto;

@Repository
public interface IssueQueryRepository {

	List<IssueDto.BaseRow> findIssuesWithFilters(
		IssueState state, Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds, CursorDto.CursorData cursor);

	IssueDto.CountResponse countIssuesWithFilters(Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds);

	int batchUpdateIssueStates(List<Integer> issueIds, IssueState action, LocalDateTime now);

	List<IssueDto.BatchIssueRow> findExistingIssuesByIds(List<Integer> issueIds);

	Integer createIssue(String title, String content, Integer writerId, Integer milestoneId, LocalDateTime now);

	IssueDto.BaseRow findCreatedIssueById(Integer issueId);

}
