package codesquad.team4.issuetracker.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class OAuthResponseDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class OAuthLoginUrlResponse {
        private String url;
    }
}
