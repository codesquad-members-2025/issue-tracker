package elbin_bank.issue_tracker.user.domain;

import java.util.List;

public interface UserCommandRepository {

    void saveAssigneesToIssue(long issueId, List<Long> assignees);

}
