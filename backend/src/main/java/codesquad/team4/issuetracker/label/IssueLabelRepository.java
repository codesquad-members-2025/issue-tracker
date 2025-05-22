package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.IssueLabel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface IssueLabelRepository extends CrudRepository<IssueLabel, Long> {
    List<IssueLabel> findAllByIssueId(Long issueId);
}
