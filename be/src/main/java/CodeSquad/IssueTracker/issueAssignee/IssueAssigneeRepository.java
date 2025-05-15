package CodeSquad.IssueTracker.issueAssignee;

import java.util.List;

public interface IssueAssigneeRepository {

    void save(IssueAssignee issueAssignee);

    void deleteByIssueId(Long issueId);


}
