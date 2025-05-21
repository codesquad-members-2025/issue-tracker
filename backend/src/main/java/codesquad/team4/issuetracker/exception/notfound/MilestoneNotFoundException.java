package codesquad.team4.issuetracker.exception.notfound;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_MILESTONE;

public class MilestoneNotFoundException extends DataNotFoundException {

    public MilestoneNotFoundException(Long milestoneId) {
        super(NOT_FOUND_MILESTONE+ milestoneId);
    }
}
