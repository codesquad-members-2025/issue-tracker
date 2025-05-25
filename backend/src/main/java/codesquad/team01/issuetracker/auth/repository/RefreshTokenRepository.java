package codesquad.team01.issuetracker.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.auth.domain.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

	Optional<RefreshToken> findByUserId(Integer userId);
}
