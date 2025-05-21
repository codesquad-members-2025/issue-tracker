package codesquad.team01.issuetracker.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.user.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByLoginId(Integer id);

	Optional<User> findByProviderIdAndAuthProvider(Long providerId, String authProvider);
}
