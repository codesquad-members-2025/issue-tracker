package elbin_bank.issue_tracker.label.application.query.repository;

import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;

import java.util.List;
import java.util.Map;

public interface LabelQueryRepository {

    Map<Long, List<LabelProjection>> findByIssueIds(List<Long> issueIds);

    List<LabelProjection> findAll();

}
