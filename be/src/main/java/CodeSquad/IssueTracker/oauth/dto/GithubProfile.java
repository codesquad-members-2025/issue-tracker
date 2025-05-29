package CodeSquad.IssueTracker.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubProfile {

    @JsonProperty("email")
    private String loginId;

    @JsonProperty("name")
    private String nickname;

    @JsonProperty("imgUrl")
    private String profileImageUrl;
}
