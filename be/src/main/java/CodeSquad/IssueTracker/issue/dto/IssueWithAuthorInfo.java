package CodeSquad.IssueTracker.issue.dto;

import CodeSquad.IssueTracker.issue.Issue;
import CodeSquad.IssueTracker.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class IssueWithAuthorInfo {
    private Long issueId;
    private String title;
    private String content;
    private Long authorId;
    private String authorNickname;
    private Long milestoneId;
    private Boolean isOpen;
    private LocalDateTime lastModifiedAt;
    private String issueFileUrl;
    private String authorProfileUrl;

    public static IssueWithAuthorInfo from(Issue issue, User author) {
        return IssueWithAuthorInfo.builder()
                .issueId(issue.getIssueId())
                .title(issue.getTitle())
                .content(issue.getContent())
                .authorId(issue.getAuthorId())
                .authorNickname(author.getNickName())
                .milestoneId(issue.getMilestoneId())
                .isOpen(issue.getIsOpen())
                .lastModifiedAt(issue.getLastModifiedAt())
                .issueFileUrl(issue.getIssueFileUrl())
                .authorProfileUrl(author.getProfileImageUrl())
                .build();
    }
}
