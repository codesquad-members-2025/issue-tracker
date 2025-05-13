package CodeSquad.IssueTracker.user.repository;

import CodeSquad.IssueTracker.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByLoginId(String loginId);
}
