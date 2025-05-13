package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.entity.Issue;
import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<Issue, Long> {
}
