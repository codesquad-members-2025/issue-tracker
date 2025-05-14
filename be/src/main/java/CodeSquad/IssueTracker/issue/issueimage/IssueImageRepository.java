package CodeSquad.IssueTracker.issue.issueimage;

import java.util.List;

public interface IssueImageRepository {
    IssueImage save(IssueImage image);
    List<IssueImage> findByIssueId(Long issueId);
}
