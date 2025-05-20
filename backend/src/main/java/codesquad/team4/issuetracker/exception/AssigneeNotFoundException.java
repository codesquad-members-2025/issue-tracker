package codesquad.team4.issuetracker.exception;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_ASSIGNEE;

import java.util.Set;

public class AssigneeNotFoundException extends RuntimeException {

    public AssigneeNotFoundException(Set<Long> assigneeIds) {
        super(NOT_FOUND_ASSIGNEE + assigneeIds);
    }
}
