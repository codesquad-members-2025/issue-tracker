package elbin_bank.issue_tracker.user.application.query;

import elbin_bank.issue_tracker.user.application.query.dto.UserInfoResponseDto;
import elbin_bank.issue_tracker.user.application.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryRepository userQueryRepository;

    @Transactional(readOnly = true)
    public UserInfoResponseDto findUsers() {
        return new UserInfoResponseDto(userQueryRepository.findAll());
    }

}
