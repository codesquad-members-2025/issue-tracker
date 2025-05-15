package CodeSquad.IssueTracker.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByLoginId(String loginId);
}
