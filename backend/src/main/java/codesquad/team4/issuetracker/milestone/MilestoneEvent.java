package codesquad.team4.issuetracker.milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MilestoneEvent {
    @Getter
    public static class Created { } //open + 1

    @AllArgsConstructor
    @Getter
    public static class StatusChanged {
        private final boolean oldIsOpen;
        private final boolean newIsOpen;
    }

    @AllArgsConstructor
    @Getter
    public static class Deleted {
        private final boolean wasOpen;
    }
}
