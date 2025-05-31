package elbin_bank.issue_tracker.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserCommandRepository {

    void saveAssigneesToIssue(long issueId, List<Long> assignees);

    User save(User user);

    Optional<User> findByLogin(String login);

    Optional<User> findByOAuthId(long oauthId);

    void deleteAssigneesFromIssue(long issueId, List<Long> assignees);

}
