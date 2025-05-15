package CodeSquad.IssueTracker.issueLabel;

public interface IssueLabelRepository {

    void save(IssueLabel issueLabel);

    void deleteByIssueId(Long issueId);
}
