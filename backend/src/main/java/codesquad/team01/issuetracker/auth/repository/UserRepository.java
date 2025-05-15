package codesquad.team01.issuetracker.auth.repository;

import codesquad.team01.issuetracker.auth.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
}
