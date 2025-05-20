package codesquad.team4.issuetracker.exception;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_LABEL;

import java.util.Set;

public class LabelNotFoundException extends RuntimeException {

    public LabelNotFoundException(Set<Long> labelIds) {
        super(NOT_FOUND_LABEL + labelIds);
    }
}
