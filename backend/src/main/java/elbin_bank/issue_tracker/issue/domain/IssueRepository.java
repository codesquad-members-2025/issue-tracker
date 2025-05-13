package elbin_bank.issue_tracker.issue.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long> {

    List<Issue> findByFilter(Boolean isClosed);

    long countByStatus(IssueStatus status);

}
