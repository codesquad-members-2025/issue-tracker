package CodeSquad.IssueTracker.issueAssignee;

import CodeSquad.IssueTracker.issueAssignee.dto.IssueAssigneeResponse;

import java.util.List;

public interface IssueAssigneeRepository {

    void save(IssueAssignee issueAssignee);

    void deleteByIssueId(Long issueId);

    List<IssueAssigneeResponse> findAssigneeResponsesByIssueId(Long issueId);


}
