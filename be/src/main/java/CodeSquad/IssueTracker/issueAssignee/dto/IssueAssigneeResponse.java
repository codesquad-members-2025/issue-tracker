package CodeSquad.IssueTracker.issueAssignee.dto;

public class IssueAssigneeResponse {
    private Long id;
    private String nickname;
    private String profileImageUrl;


    public IssueAssigneeResponse(Long id, String nickname, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
