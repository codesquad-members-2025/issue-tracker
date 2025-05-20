package codesquad.team4.issuetracker.user;

import codesquad.team4.issuetracker.response.ApiResponse;
import codesquad.team4.issuetracker.user.dto.UserDto;
import codesquad.team4.issuetracker.user.dto.UserDto.UserFilter;
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
    public ResponseEntity<ApiResponse<UserFilter>> getFilterUsers() {
        UserDto.UserFilter result = userService.getFilterUsers();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
