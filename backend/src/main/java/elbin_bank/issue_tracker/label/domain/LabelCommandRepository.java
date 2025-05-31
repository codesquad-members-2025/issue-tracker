package elbin_bank.issue_tracker.label.domain;

import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import elbin_bank.issue_tracker.label.presentation.command.dto.request.LabelUpdateRequestDto;

import java.util.List;

public interface LabelCommandRepository {

    void saveLabelsToIssue(Long issueId, List<Long> labelIds);

    void save(Label label);

    void update(LabelProjection label, LabelUpdateRequestDto labelUpdateRequestDto);
}
