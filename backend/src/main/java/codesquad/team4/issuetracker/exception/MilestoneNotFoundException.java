package codesquad.team4.issuetracker.exception;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_MILESTONE;

public class MilestoneNotFoundException extends RuntimeException {

    public MilestoneNotFoundException(Long milestoneId) {
        super(NOT_FOUND_MILESTONE+ milestoneId);
    }
}
