package codesquad.team4.issuetracker.exception;

import java.util.Set;

public class AssigneeNotFoundException extends RuntimeException {
    private final Set<Long> assigneeIds;

    public AssigneeNotFoundException(Set<Long> assigneeIds) {
        super("존재하지 않는 assignee ID: " + assigneeIds);
        this.assigneeIds = assigneeIds;
    }

    public Set<Long> getIssueId() {
        return assigneeIds;
    }
}
