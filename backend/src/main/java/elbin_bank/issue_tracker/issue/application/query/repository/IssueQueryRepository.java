package elbin_bank.issue_tracker.issue.application.query.repository;

import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;

import java.util.List;
import java.util.Map;

public interface IssueQueryRepository {

    List<IssueProjection> findIssues(String rawQuery);

    Map<Long, List<String>> findAssigneesByIssueIds(List<Long> issueIds);

    IssueCountProjection countIssueOpenAndClosed();

}
