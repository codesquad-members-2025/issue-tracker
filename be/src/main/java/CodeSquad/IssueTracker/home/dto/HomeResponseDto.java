package CodeSquad.IssueTracker.home.dto;

import CodeSquad.IssueTracker.issue.Issue;
import CodeSquad.IssueTracker.label.Label;
import CodeSquad.IssueTracker.milestone.Milestone;
import CodeSquad.IssueTracker.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HomeResponseDto {
    private Iterable<Issue> issues;
    private Iterable<Label> labels;
    private Iterable<Milestone> milestones;
    private Iterable<User> users;
}
