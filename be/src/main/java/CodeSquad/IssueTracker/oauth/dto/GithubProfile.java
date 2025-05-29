package CodeSquad.IssueTracker.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubProfile {

    @JsonProperty("login")
    private String loginId;

    @JsonProperty("name")
    private String nickname;

    @JsonProperty("avatar_url")
    private String profileImageUrl;
}
