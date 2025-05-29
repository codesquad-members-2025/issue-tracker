package codesquad.team4.issuetracker.util;

import lombok.Getter;

@Getter
public enum OpenStatus{
    OPEN("open", true), CLOSE("close", false);

    private final String value;
    private final boolean state;
    private static final OpenStatus[] VALUES = OpenStatus.values();

    OpenStatus(String value, boolean state) {
        this.value = value;
        this.state = state;
    }

    public static OpenStatus fromValue(String value) {
        for (OpenStatus status : VALUES) {
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
