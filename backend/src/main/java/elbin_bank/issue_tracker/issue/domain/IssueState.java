package elbin_bank.issue_tracker.issue.domain;

import java.util.Arrays;
import java.util.Optional;

public enum IssueState {

    OPEN("open"),
    CLOSED("closed");

    private final String state;

    IssueState(String state) {
        this.state = state;
    }

    public static Optional<IssueState> from(String raw) {
        return Arrays.stream(values())
                .filter(e -> e.state.equalsIgnoreCase(raw))
                .findFirst();
    }

}
