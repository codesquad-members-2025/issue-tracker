package codesquad.team01.issuetracker.user.repository;

import codesquad.team01.issuetracker.user.domain.User;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByLoginId(int id);

	Optional<User> findByProviderIdAndAuthProvider(Long providerId, String authProvider);
}
