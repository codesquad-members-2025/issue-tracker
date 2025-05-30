package elbin_bank.issue_tracker.issue.domain;

import java.util.Optional;

public interface IssueCommandRepository {

    Optional<Issue> findById(long id);

    Issue save(Issue issue);

    void updateState(long issueId, boolean isClosed);

}
