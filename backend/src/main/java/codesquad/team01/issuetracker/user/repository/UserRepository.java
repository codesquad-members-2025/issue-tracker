package codesquad.team01.issuetracker.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import codesquad.team01.issuetracker.user.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByLoginId(Integer id);

	Optional<User> findByProviderIdAndAuthProvider(Long providerId, String authProvider);
}
