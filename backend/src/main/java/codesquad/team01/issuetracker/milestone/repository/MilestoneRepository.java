package codesquad.team01.issuetracker.milestone.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import codesquad.team01.issuetracker.milestone.domain.Milestone;
import codesquad.team01.issuetracker.milestone.domain.MilestoneState;

public interface MilestoneRepository extends CrudRepository<Milestone, Integer>, MilestoneQueryRepository {

	List<Milestone> findByState(MilestoneState state);
}
