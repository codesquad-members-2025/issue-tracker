package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.Milestone;
import org.springframework.data.repository.CrudRepository;

public interface MilestoneRepository extends CrudRepository<Milestone, Long> {
}
