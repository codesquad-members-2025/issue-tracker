package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.entity.Issue;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long> {
    List<Issue> findAllByIdIn(Collection<Long> ids);
}
