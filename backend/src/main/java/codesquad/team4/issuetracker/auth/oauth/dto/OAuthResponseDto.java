package codesquad.team4.issuetracker.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class OAuthResponseDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class OAuthLoginUrl {
        private String url;
    }

    //외부 API(GitHub)응답 받기 위한 Dto
    @Getter
    @Setter
    @AllArgsConstructor
    public static class GitHubUserResponse {
        private Long id;
        private String login;
        private String email;
        @JsonProperty("avatar_url")
        private String avatarUrl;
        @JsonProperty("name")
        private String nickname;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GitHubEmailResponse{
        private String email;
        private boolean primary;
        private boolean verified;
    }
}
