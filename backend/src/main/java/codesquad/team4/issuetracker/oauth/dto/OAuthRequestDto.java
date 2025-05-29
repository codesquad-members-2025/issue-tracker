package codesquad.team4.issuetracker.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class OAuthRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class GitHubCallback {
        private String code;
        private String state;
    }
}
