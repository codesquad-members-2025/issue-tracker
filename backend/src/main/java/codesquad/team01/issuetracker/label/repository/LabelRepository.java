package codesquad.team01.issuetracker.label.repository;

import org.springframework.data.repository.CrudRepository;

import codesquad.team01.issuetracker.label.domain.Label;

public interface LabelRepository extends CrudRepository<Label, Integer> {
}
