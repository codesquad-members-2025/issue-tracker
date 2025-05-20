package codesquad.team01.issuetracker.issue.repository;

import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.issue.dto.IssueDto;

import java.util.List;

public interface IssueQueryRepository {

    List<IssueDto.BaseRow> findIssuesWithFilters(
            IssueState state, Long writerId, Long milestoneId,
            List<Long> labelIds, List<Long> assigneeIds);

}
