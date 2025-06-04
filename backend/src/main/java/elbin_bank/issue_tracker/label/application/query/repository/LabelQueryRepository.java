package elbin_bank.issue_tracker.label.application.query.repository;

import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LabelQueryRepository {

    Optional<LabelProjection> findById(Long id);

    Map<Long, List<LabelProjection>> findByIssueIds(List<Long> issueIds);

    List<Long> findLabelIdsByIssueId(long issueId);

    List<LabelProjection> findAll();

}
