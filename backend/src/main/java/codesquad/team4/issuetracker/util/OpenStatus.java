package codesquad.team4.issuetracker.util;

import lombok.Getter;

@Getter
public enum OpenStatus{
    OPEN("open", true), CLOSE("close", false);

    final String value;
    final boolean state;

    OpenStatus(String value, boolean state) {
        this.value = value;
        this.state = state;
    }
    public static OpenStatus fromValue(String value) {
        for (OpenStatus status : OpenStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return OpenStatus.OPEN;
    }
    public boolean getState() {
        return state;
    }
}
