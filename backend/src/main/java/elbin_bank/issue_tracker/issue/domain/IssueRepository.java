package elbin_bank.issue_tracker.issue.domain;

import java.util.List;

public interface IssueRepository {

    List<Issue> findByFilter(Boolean isClosed);

    long countByStatus(IssueStatus status);

}
