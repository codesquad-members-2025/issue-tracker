package elbin_bank.issue_tracker.user.presentation.query;

import elbin_bank.issue_tracker.user.application.query.UserQueryService;
import elbin_bank.issue_tracker.user.application.query.dto.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("")
    public ResponseEntity<UserInfoResponseDto> getUsers() {
        return ResponseEntity.ok(userQueryService.findUsers());
    }

}
