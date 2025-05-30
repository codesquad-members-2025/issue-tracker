package codesquad.team4.issuetracker.milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MilestoneEvent {
    @Getter
    public static class Created { } //open + 1

    @AllArgsConstructor
    @Getter
    public static class StatusChanged { //상태 변경 old -> new
        private final boolean oldIsOpen;
        private final boolean newIsOpen;
        //open -> closed
        public boolean isClosing() {
            return oldIsOpen && !newIsOpen;
        }
        //closed -> open
        public boolean isOpening() {
            return !oldIsOpen && newIsOpen;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Deleted {
        private final boolean wasOpen;
    }
}
