package elbin_bank.issue_tracker.issue.application.query.repository;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IssueQueryRepository {

    List<IssueProjection> findIssues(FilterCriteria crit);

    Map<Long, List<String>> findAssigneeNamesByIssueIds(List<Long> issueIds);

    List<UserInfoProjection> findAssigneesByIssueId(long id);

    IssueCountProjection countIssueOpenAndClosed();

    Optional<IssueDetailProjection> findById(long id);

}
