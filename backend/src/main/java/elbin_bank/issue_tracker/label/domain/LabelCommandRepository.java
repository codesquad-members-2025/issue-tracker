package elbin_bank.issue_tracker.label.domain;

import java.util.List;

public interface LabelCommandRepository {

    void saveLabelsToIssue(Long issueId, List<Long> labelIds);

}
