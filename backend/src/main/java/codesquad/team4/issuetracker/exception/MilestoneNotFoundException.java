package codesquad.team4.issuetracker.exception;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_MILESTONE;

public class MilestoneNotFoundException extends RuntimeException {
    private final Long milestoneId;

    public MilestoneNotFoundException(Long milestoneId) {
        super(NOT_FOUND_MILESTONE+ milestoneId);
        this.milestoneId = milestoneId;
    }

    public Long getMilestoneId() {
        return milestoneId;
    }
}
