package elbin_bank.issue_tracker.milestone.domain;

import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneUpdateProjection;

import java.time.LocalDate;

public interface MilestoneCommandRepository {

    void save(Milestone milestone);

    void update(MilestoneUpdateProjection milestone, String title, String description, LocalDate expiredAt);

    void deleteById(Long id);
}
