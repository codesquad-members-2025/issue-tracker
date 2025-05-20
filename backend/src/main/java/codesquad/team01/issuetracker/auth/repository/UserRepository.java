package codesquad.team01.issuetracker.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import codesquad.team01.issuetracker.auth.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByLoginId(String loginId);
}
