package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.entity.Label;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository extends CrudRepository<Label, Long> {
}
