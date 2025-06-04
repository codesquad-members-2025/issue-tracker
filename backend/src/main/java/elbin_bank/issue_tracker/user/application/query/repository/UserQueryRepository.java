package elbin_bank.issue_tracker.user.application.query.repository;

import elbin_bank.issue_tracker.user.infrastructure.query.projection.UserProjection;

import java.util.List;
import java.util.Map;

public interface UserQueryRepository {

    Map<Long, String> findNickNamesByIds(List<Long> ids);

    Map<Long, UserProjection> findUsersByIds(List<Long> ids);

    List<UserProjection> findAll();

}
