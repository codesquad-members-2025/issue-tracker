package elbin_bank.issue_tracker.issue.domain;

import elbin_bank.issue_tracker.label.domain.Label;

import java.util.List;
import java.util.Map;

public interface IssueRepository {

    List<Issue> findByFilter(Boolean isClosed);

    long countByStatus(IssueStatus status);

    Map<Long, List<Label>> findLabelsByIssueIds(List<Long> issueIds);

    Map<Long, List<String>> findAssigneesByIssueIds(List<Long> issueIds);

}
