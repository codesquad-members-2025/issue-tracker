package elbin_bank.issue_tracker.milestone.application.query.repository;

import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneProjection;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneShortProjection;

import java.util.List;
import java.util.Optional;

public interface MilestoneQueryRepository {

    List<MilestoneShortProjection> findAllForSelectBox();

    List<MilestoneProjection> findAllDetailed();

    Optional<MilestoneProjection> findByIssueId(long issueId);

}
