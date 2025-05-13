package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.user.dto.UserFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/filter")
    public ResponseEntity<UserFilterDto> getFilterUsers() {
        UserFilterDto result = userService.getFilterUsers();
        return ResponseEntity.ok(result);
    }

}
