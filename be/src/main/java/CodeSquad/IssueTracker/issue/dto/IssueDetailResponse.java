package CodeSquad.IssueTracker.issue.dto;

import CodeSquad.IssueTracker.comment.Comment;
import CodeSquad.IssueTracker.issue.Issue;
import CodeSquad.IssueTracker.issueAssignee.dto.IssueAssigneeResponse;
import CodeSquad.IssueTracker.label.dto.LabelResponse;
import CodeSquad.IssueTracker.milestone.dto.MilestoneResponse;
import lombok.Data;

import java.util.List;

@Data
public class IssueDetailResponse {
    private Issue issue;

    private List<IssueAssigneeResponse> assignees;
    private List<LabelResponse> labels;
    private MilestoneResponse milestone;

    private List<Comment> comments;
}

