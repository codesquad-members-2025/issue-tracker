package codesquad.team4.issuetracker.exception;

import java.util.Set;

public class LabelNotFoundException extends RuntimeException {
    private final Set<Long> labelIds;

    public LabelNotFoundException(Set<Long> labelIds) {
        super("존재하지 않는 label ID: " + labelIds);
        this.labelIds = labelIds;
    }

    public Set<Long> getIssueId() {
        return labelIds;
    }
}
