package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailOrNickname(String email, String nickname);
}
