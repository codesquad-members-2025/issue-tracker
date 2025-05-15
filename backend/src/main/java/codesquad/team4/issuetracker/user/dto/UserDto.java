package codesquad.team4.issuetracker.user.dto;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

public class UserDto {

    @AllArgsConstructor
    @Getter
    @Builder
    @EqualsAndHashCode
    public static class UserInfo {
        private Long id;
        private String nickname;
        private String profileImage;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class UserFilter {
        private List<UserInfo> users;
    }
}
