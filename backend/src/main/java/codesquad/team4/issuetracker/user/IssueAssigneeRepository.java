package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.entity.IssueAssignee;
import org.springframework.data.repository.CrudRepository;

public interface IssueAssigneeRepository extends CrudRepository<IssueAssignee, Long> {
}
