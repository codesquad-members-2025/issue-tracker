package elbin_bank.issue_tracker.milestone.domain;

import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneUpdateProjection;

import java.time.LocalDate;
import java.util.Optional;

public interface MilestoneCommandRepository {

    void save(Milestone milestone);

    void update(MilestoneUpdateProjection milestone, String title, String description, LocalDate expiredAt);

    void deleteById(Long id);

    void adjustTotalIssues(long milestoneId, long delta);

    void adjustClosedIssues(long milestoneId, long delta);

    Optional<Milestone> findById(Long id);

    void updateState(long id, boolean isClosed);

}
