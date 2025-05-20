package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.entity.IssueAssignee;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface IssueAssigneeRepository extends CrudRepository<IssueAssignee, Long> {
    List<IssueAssignee> findAllByIssueId(Long issueId);
}
