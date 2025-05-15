package CodeSquad.IssueTracker.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

}
