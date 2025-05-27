package codesquad.team4.issuetracker.issue;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class IssueEvent {

    @Getter
    public static class Created { } //open + 1

    @AllArgsConstructor
    @Getter
    public static class StatusChanged { //상태 변경 oㅣd -> new
        private final boolean oldIsOpen;
        private final boolean newIsOpen;
    }

    @AllArgsConstructor
    @Getter
    public static class Deleted { //삭제 전 open여부
        private final boolean wasOpen;
    }


}
