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

	IssueDto.DetailBaseRow findCreatedIssueById(Integer issueId);

	void addLabelsToIssue(Integer issueId, List<Integer> labelIds);

	void addAssigneesToIssue(Integer issueId, List<Integer> assigneeIds);

	void removeLabelsFromIssue(Integer issueId);

	void removeAssigneesFromIssue(Integer issueId);

	void updateIssueTitle(Integer issueId, String title, LocalDateTime now);

	void updateIssueContent(Integer issueId, String content, LocalDateTime now);

	void updateIssueMilestone(Integer issueId, Integer integer, LocalDateTime now);

	void updateIssueState(Integer issueId, IssueState targetState, LocalDateTime now);

	IssueDto.IssueStateAndWriterIdRow findIssueStateAndWriterIdByIssueId(Integer issueId);
}
