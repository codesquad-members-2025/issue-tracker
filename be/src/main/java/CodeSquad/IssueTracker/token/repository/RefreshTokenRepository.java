package CodeSquad.IssueTracker.token.repository;

import CodeSquad.IssueTracker.token.RefreshToken;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);
}
