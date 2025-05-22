package elbin_bank.issue_tracker.milestone.domain;

import java.util.List;
import java.util.Map;

public interface MilestoneRepository {

    Map<Long, String> findTitlesByIds(List<Long> ids);

}
