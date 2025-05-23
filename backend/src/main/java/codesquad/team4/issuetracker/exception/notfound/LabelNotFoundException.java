package codesquad.team4.issuetracker.exception.notfound;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_LABEL;

import java.util.Set;

public class LabelNotFoundException extends DataNotFoundException {

    public LabelNotFoundException(Set<Long> labelIds) {
        super(NOT_FOUND_LABEL + labelIds);
    }

    public LabelNotFoundException(Long labelId) { super(NOT_FOUND_LABEL + labelId);}
}
